package com.cybersec.blackwallpass.ui.screens.auth.data

import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.core.exception.AuthException
import com.cybersec.blackwallpass.core.exception.BaseException
import com.cybersec.blackwallpass.core.exception.NetworkException
import com.cybersec.blackwallpass.core.exception.UnknownException
import com.cybersec.blackwallpass.ui.screens.auth.domain.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val supabase: SupabaseClient
) : AuthRepository {
	override suspend fun signUp(email: String, masterPassword: String): Result<Unit> {
		return try {
			supabase.auth.signUpWith(Email) {
				this.email = email
				this.password = masterPassword
			}
			Result.success(Unit)
		} catch (e: AuthRestException) {
			Result.failure(mapAuthException(e))
		} catch (e: HttpRequestException) {
			Result.failure(NetworkException(R.string.error_network, e.message))
		} catch (e: RestException) {
			Result.failure(NetworkException(R.string.error_network, e.message))
		} catch (e: Exception) {
			Result.failure(UnknownException(R.string.error_unknown, e.message))
		}
	}

	override suspend fun signIn(email: String, masterPassword: String): Result<Unit> {
		return try {
			supabase.auth.signInWith(Email) {
				this.email = email
				this.password = masterPassword
			}
			Result.success(Unit)
		} catch (e: AuthRestException) {
			Result.failure(mapAuthException(e))
		} catch (e: HttpRequestException) {
			Result.failure(NetworkException(R.string.error_network, e.message))
		} catch (e: RestException) {
			Result.failure(NetworkException(R.string.error_network, e.message))
		} catch (e: Exception) {
			Result.failure(UnknownException(R.string.error_unknown, e.message))
		}
	}

	override suspend fun signOut(): Result<Unit> {
		return try {
			supabase.auth.signOut()
			Result.success(Unit)
		} catch (e: Exception) {
			supabase.auth.sessionManager.deleteSession()
			Result.success(Unit)
		}
	}

	override fun getCurrentUserId(): String? = supabase.auth.currentUserOrNull()?.id

	private fun mapAuthException(e: AuthRestException): BaseException {
		val messageId = when (e.errorCode) {
			// Регистрация
			AuthErrorCode.UserAlreadyExists -> R.string.error_user_already_exists
			AuthErrorCode.EmailExists -> R.string.error_user_already_exists
			AuthErrorCode.WeakPassword -> R.string.error_weak_password

			// Вход
			AuthErrorCode.InvalidCredentials -> R.string.error_invalid_credentials
			AuthErrorCode.UserNotFound -> R.string.error_invalid_credentials
			AuthErrorCode.UserBanned -> R.string.error_user_banned

			// Валидация
			AuthErrorCode.ValidationFailed -> R.string.error_validation_failed
			AuthErrorCode.EmailAddressInvalid -> R.string.error_email_invalid
			AuthErrorCode.EmailNotConfirmed -> R.string.error_email_not_confirmed

			// Лимиты
			AuthErrorCode.OverEmailSendRateLimit -> R.string.error_too_many_requests
			AuthErrorCode.OverRequestRateLimit -> R.string.error_too_many_requests
			AuthErrorCode.RequestTimeout -> R.string.error_timeout

			// Смена пароля
			AuthErrorCode.SamePassword -> R.string.error_same_password

			// Общее
			AuthErrorCode.UnexpectedFailure -> R.string.error_unknown
			else -> R.string.error_unknown
		}
		return AuthException(messageId, e.message)
	}
}