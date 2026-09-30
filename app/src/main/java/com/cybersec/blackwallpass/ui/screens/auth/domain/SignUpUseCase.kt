package com.cybersec.blackwallpass.ui.screens.auth.domain

import android.util.Base64
import android.util.Patterns
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.core.exception.UnknownException
import com.cybersec.blackwallpass.core.exception.ValidationException
import com.cybersec.blackwallpass.crypto.CryptoManager
import com.cybersec.blackwallpass.data.model.UserSecurity
import com.cybersec.blackwallpass.data.repository.UserSecurityRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
	private val authRepository: AuthRepository,
	private val userSecurityRepository: UserSecurityRepository
) {
	suspend operator fun invoke(email: String, masterPassword: String, confirmMasterPassword: String): Result<Unit> {
		if (email.isBlank()) {
			return Result.failure(ValidationException(R.string.error_email_empty))
		}

		if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			return Result.failure(ValidationException(R.string.error_email_invalid))
		}

		if (masterPassword.isBlank()) {
			return Result.failure(ValidationException(R.string.error_master_password_empty))
		}

		if (masterPassword.length < 12) {
			return Result.failure(ValidationException(R.string.error_master_password_too_short))
		}

		if (masterPassword != confirmMasterPassword) {
			return Result.failure(ValidationException(R.string.error_passwords_dont_match))
		}

		val signUpResult = authRepository.signUp(email, masterPassword)
		if (signUpResult.isFailure) {
			return signUpResult
		}

		val userId = authRepository.getCurrentUserId() ?: run {
			authRepository.signOut()
			return Result.failure(UnknownException(R.string.error_unknown))
		}
		val saltBytes = CryptoManager.generateSalt()
		val saltBase64 = Base64.encodeToString(saltBytes, Base64.NO_WRAP)

		val key = CryptoManager.deriveKey(masterPassword, saltBytes)
		val encryptCheckString = CryptoManager.encryptCheckString(key)

		val userSecurity = UserSecurity(
			userId = userId,
			encryptedCheckString = encryptCheckString,
			salt = saltBase64
		)

		return try {
			userSecurityRepository.save(userSecurity)
			Result.success(Unit)
		} catch (e: Exception) {
			authRepository.signOut()
			Result.failure(UnknownException(R.string.error_unknown, e.message))
		}
	}
}