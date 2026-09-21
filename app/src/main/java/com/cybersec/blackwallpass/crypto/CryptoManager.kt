package com.cybersec.blackwallpass.crypto

import android.util.Base64
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2Mode
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptoManager {
	private const val SALT_LENGTH_BYTES = 16
	private const val ARGON2_ITERATIONS = 2
	private const val ARGON2_MEMORY_KIB = 19456

	private const val IV_LENGTH_BYTES = 12
	private const val TAG_LENGTH_BITS = 128
	private const val AES_ALGORITHM = "AES/GCM/NoPadding"

	private const val CHECK_STRING = "Blackwall_Verified"

	private val secureRandom = SecureRandom()
	private val argon2Kt = Argon2Kt()

	//Генерация соли
	fun generateSalt(): ByteArray {
		val salt = ByteArray(SALT_LENGTH_BYTES)
		secureRandom.nextBytes(salt)
		return salt
	}

	//Генерация ключа
	fun deriveKey(masterPassword: String, salt: ByteArray): ByteArray {
		val result = argon2Kt.hash(
			mode = Argon2Mode.ARGON2_ID,
			password = masterPassword.toByteArray(),
			salt = salt,
			tCostInIterations = ARGON2_ITERATIONS,
			mCostInKibibyte = ARGON2_MEMORY_KIB
		)
		return result.rawHashAsByteArray()
	}

	//Шифрование пароля
	fun encrypt(key: ByteArray, plaintext: String): String {
		val iv = ByteArray(IV_LENGTH_BYTES)
		secureRandom.nextBytes(iv)

		val cipher = Cipher.getInstance(AES_ALGORITHM)
		val spec = GCMParameterSpec(TAG_LENGTH_BITS, iv)
		val secretKey = SecretKeySpec(key, "AES")
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)

		val ciphertext = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))
		val combined = iv + ciphertext

		return Base64.encodeToString(combined, Base64.NO_WRAP)
	}

	//Расшифровка пароля
	fun decrypt(key: ByteArray, encryptedBase64: String): String {
		val combined = Base64.decode(encryptedBase64, Base64.NO_WRAP)

		val iv = combined.copyOfRange(0, IV_LENGTH_BYTES)
		val ciphertext = combined.copyOfRange(IV_LENGTH_BYTES, combined.size)

		val cipher = Cipher.getInstance(AES_ALGORITHM)
		val spec = GCMParameterSpec(TAG_LENGTH_BITS, iv)
		val secretKey = SecretKeySpec(key, "AES")
		cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

		val plaintext = cipher.doFinal(ciphertext)

		return String(plaintext, Charsets.UTF_8)
	}

	//Шифруем ключевое слово ключем
	fun encryptCheckString(key: ByteArray): String = encrypt(key, CHECK_STRING)

	//Метод проверки матера пароля, путем расшифровывания ключевого слова
	fun verifyMasterPassword(masterPassword: String, salt: ByteArray, encryptedCheckString: String): Boolean {
		return try {
			val key = deriveKey(masterPassword, salt)
			val decrypted = decrypt(key, encryptedCheckString)
			decrypted == CHECK_STRING
		} catch (e: Exception) {
			false
		}
	}
}