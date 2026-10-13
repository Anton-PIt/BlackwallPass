package com.cybersec.blackwallpass.ui.screens.auth.domain

import android.util.Base64
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
		AuthValidator.validateEmail(email)?.let {
			return Result.failure(ValidationException(it))
		}
		AuthValidator.validateMasterPassword(masterPassword)?.let {
			return Result.failure(ValidationException(it))
		}
		AuthValidator.validateConfirmPassword(masterPassword, confirmMasterPassword)?.let {
			return Result.failure(ValidationException(it))
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