package com.cybersec.blackwallpass.ui.screens.auth.domain

import android.util.Patterns
import androidx.annotation.StringRes
import com.cybersec.blackwallpass.R

object AuthValidator {
	private const val MIN_PASSWORD_LENGTH = 12

	@StringRes
	fun validateEmail(email: String): Int? = when {
		email.isBlank() -> R.string.error_email_empty
		!Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_email_invalid
		else -> null
	}

	@StringRes
	fun validateMasterPassword(password: String): Int? = when {
		password.isBlank() -> R.string.error_master_password_empty
		password.length < MIN_PASSWORD_LENGTH -> R.string.error_master_password_too_short
		else -> null
	}

	@StringRes
	fun validateConfirmPassword(password: String, confirm: String): Int? = when {
		password != confirm -> R.string.error_passwords_dont_match
		else -> null
	}
}