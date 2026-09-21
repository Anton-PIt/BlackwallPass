package com.cybersec.blackwallpass.crypto

import org.junit.Assert
import org.junit.Test
import javax.crypto.AEADBadTagException

class CryptoManagerTest {
	//Тесты генерации соли (функции generateSalt())
	@Test
	fun generateSalt_returns16Bytes() {
		val salt = CryptoManager.generateSalt()
		Assert.assertEquals(16, salt.size)
	}

	@Test
	fun generateSalt_returnsDifferentValuesOnEachCall() {
		val salt1 = CryptoManager.generateSalt()
		val salt2 = CryptoManager.generateSalt()
		Assert.assertNotEquals(salt1.toList(), salt2.toList())
	}

	@Test
	fun generateSalt_doesNotReturnAllZeros() {
		val salt = CryptoManager.generateSalt()
		val allZero = ByteArray(16)
		Assert.assertFalse(salt.contentEquals(allZero))
	}

	//Тесты генерации ключа (функции deriveKey())
	@Test
	fun deriveKey_returns32Bytes() {
		val salt = CryptoManager.generateSalt()
		val key = CryptoManager.deriveKey("MySecretMasterKey", salt)
		Assert.assertEquals(32, key.size)
	}

	@Test
	fun deriveKey_isDeterministic() {
		val salt = CryptoManager.generateSalt()
		val key1 = CryptoManager.deriveKey("MySecretMasterKey", salt)
		val key2 = CryptoManager.deriveKey("MySecretMasterKey", salt)
		Assert.assertArrayEquals(key1, key2)
	}

	@Test
	fun deriveKey_differentSaltsProduceDifferentKeys() {
		val salt1 = CryptoManager.generateSalt()
		val salt2 = CryptoManager.generateSalt()
		val key1 = CryptoManager.deriveKey("MySecretMasterKey", salt1)
		val key2 = CryptoManager.deriveKey("MySecretMasterKey", salt2)
		Assert.assertFalse(key1.contentEquals(key2))
	}

	@Test
	fun deriveKey_differentPasswordsProduceDifferentKeys() {
		val salt = CryptoManager.generateSalt()
		val key1 = CryptoManager.deriveKey("PasswordOne", salt)
		val key2 = CryptoManager.deriveKey("PasswordTwo", salt)
		Assert.assertFalse(key1.contentEquals(key2))
	}

	//Тесты шифрования и расшифровки
	@Test
	fun encrypt_decrypt_returnsOriginalText() {
		val salt = CryptoManager.generateSalt()
		val key = CryptoManager.deriveKey("MySecretMasterKey", salt)
		val originalText = "mypassword123"

		val encrypted = CryptoManager.encrypt(key, originalText)
		val decrypted = CryptoManager.decrypt(key, encrypted)

		Assert.assertEquals(originalText, decrypted)
	}

	@Test
	fun encrypt_sameInputProducesDifferentOutputs() {
		val salt = CryptoManager.generateSalt()
		val key = CryptoManager.deriveKey("MySecretMasterKey", salt)
		val originalText = "mypassword123"

		val encrypted1 = CryptoManager.encrypt(key, originalText)
		val encrypted2 = CryptoManager.encrypt(key, originalText)

		Assert.assertNotEquals(encrypted1, encrypted2)
	}

	@Test
	fun decrypt_withWrongKey_throwsException() {
		val salt = CryptoManager.generateSalt()
		val correctKey = CryptoManager.deriveKey("CorrectPassword", salt)
		val wrongKey = CryptoManager.deriveKey("WrongPassword", salt)

		val encrypted = CryptoManager.encrypt(correctKey, "mypassword123")

		Assert.assertThrows(AEADBadTagException::class.java) {
			CryptoManager.decrypt(wrongKey, encrypted)
		}
	}

	@Test
	fun decrypt_withTamperedData_throwsException() {
		val salt = CryptoManager.generateSalt()
		val key = CryptoManager.deriveKey("MySecretMasterKey", salt)

		val encrypted = CryptoManager.encrypt(key, "mypassword123")

		val tampered = encrypted.substring(0, 10) + "X" + encrypted.substring(11)

		Assert.assertThrows(AEADBadTagException::class.java) {
			CryptoManager.decrypt(key, tampered)
		}
	}

	@Test
	fun encrypt_decrypt_handlesCyrillicAndEmoji() {
		val salt = CryptoManager.generateSalt()
		val key = CryptoManager.deriveKey("MySecretMasterKey", salt)
		val originalText = "Пароль123\uD83D\uDE00"

		val encrypted = CryptoManager.encrypt(key, originalText)
		val decrypted = CryptoManager.decrypt(key, encrypted)

		Assert.assertEquals(originalText, decrypted)
	}
}