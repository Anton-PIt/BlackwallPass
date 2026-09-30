package com.cybersec.blackwallpass.ui.screens.auth.domain

import android.util.Base64
import android.util.Patterns
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.core.exception.AuthException
import com.cybersec.blackwallpass.core.exception.UnknownException
import com.cybersec.blackwallpass.core.exception.ValidationException
import com.cybersec.blackwallpass.crypto.CryptoManager
import com.cybersec.blackwallpass.data.repository.UserSecurityRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
	private val authRepository: AuthRepository,
	private val userSecurityRepository: UserSecurityRepository
) {
	suspend operator fun invoke(email: String, masterPassword: String): Result<Unit> {
		if (email.isBlank()) {
			return Result.failure(ValidationException(R.string.error_email_empty))
		}

		if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			return Result.failure(ValidationException(R.string.error_email_invalid))
		}

		if (masterPassword.isBlank()) {
			return Result.failure(ValidationException(R.string.error_master_password_empty))
		}

		val signInResult = authRepository.signIn(email, masterPassword)
		if (signInResult.isFailure) {
			return signInResult
		}

		val userId = authRepository.getCurrentUserId() ?: return Result.failure(UnknownException(R.string.error_unknown))

		val userSecurity = userSecurityRepository.getByUserId(userId) ?: run {
			authRepository.signOut()
			return Result.failure(UnknownException(R.string.error_unknown))
		}

		val isValid = try {
			CryptoManager.verifyMasterPassword(
				masterPassword = masterPassword,
				salt = Base64.decode(userSecurity.salt, Base64.NO_WRAP),
				encryptedCheckString = userSecurity.encryptedCheckString
			)
		} catch (_: Exception) {
			false
		}

		if (!isValid) {
			authRepository.signOut()
			return Result.failure(AuthException(R.string.error_invalid_master_password))
		}

		return Result.success(Unit)
	}
}