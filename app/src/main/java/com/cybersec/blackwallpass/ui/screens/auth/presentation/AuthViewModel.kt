package com.cybersec.blackwallpass.ui.screens.auth.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.core.exception.AuthException
import com.cybersec.blackwallpass.core.exception.BaseException
import com.cybersec.blackwallpass.core.exception.NetworkException
import com.cybersec.blackwallpass.core.exception.ValidationException
import com.cybersec.blackwallpass.ui.common.AppViewModel
import com.cybersec.blackwallpass.ui.common.ProgressMode
import com.cybersec.blackwallpass.ui.screens.auth.domain.SignInUseCase
import com.cybersec.blackwallpass.ui.screens.auth.domain.SignUpUseCase
import com.cybersec.blackwallpass.ui.screens.auth.model.AuthTab
import com.cybersec.blackwallpass.ui.screens.auth.model.BiometricType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
	private val signInUseCase: SignInUseCase,
	private val signUpUseCase: SignUpUseCase
) : AppViewModel<AuthEvent>() {
	private val _tab = mutableStateOf(AuthTab.LOGIN)
	private val _email = mutableStateOf("")
	private val _masterPassword = mutableStateOf("")
	private val _confirmMasterPassword = mutableStateOf("")
	private val _showPassword = mutableStateOf(false)
	private val _biometricEnabled = mutableStateOf(false)
	private val _biometricType = mutableStateOf(BiometricType.FINGER)
	private val _biometricAvailable = mutableStateOf(false)
	private val _fingerprintAvailable = mutableStateOf(false)
	private val _faceAvailable = mutableStateOf(false)

	val uiState = AuthUiState(
		progressState = progressState,
		snackbarState = snackbarState,
		contentState = AuthContentUiState(
			tab = _tab,
			email = _email,
			masterPassword = _masterPassword,
			confirmMasterPassword = _confirmMasterPassword,
			showPassword = _showPassword,
			biometricEnabled = _biometricEnabled,
			biometricType = _biometricType,
			biometricAvailable = _biometricAvailable,
			fingerprintAvailable = _fingerprintAvailable,
			faceAvailable = _faceAvailable
		)
	)

	fun onTabChange(tab: AuthTab) {
		_tab.value = tab
	}

	fun onEmailChange(value: String) {
		_email.value = value
	}

	fun onMasterPasswordChange(value: String) {
		_masterPassword.value = value
	}

	fun onConfirmMasterPasswordChange(value: String) {
		_confirmMasterPassword.value = value
	}

	fun onShowPasswordToggle(showPassword: Boolean) {
		_showPassword.value = showPassword
	}

	fun onBiometricEnabledToggle(enabled: Boolean) {
		_biometricEnabled.value = enabled
	}

	fun onBiometricTypeChange(type: BiometricType) {
		_biometricType.value = type
	}

	fun onSubmit() = viewModelScope.launch {
		val email = _email.value
		val masterPassword = _masterPassword.value
		val confirmMasterPassword = _confirmMasterPassword.value

		val result = when (_tab.value) {
			AuthTab.LOGIN -> signInUseCase(email, masterPassword)
			AuthTab.SIGNUP -> signUpUseCase(email, masterPassword, confirmMasterPassword)
		}

		result
			.onSuccess { sendUiEvent(AuthEvent.CompleteEvent) }
			.onFailure { error ->
				val messageId = when (error) {
					is AuthException -> error.messageId
					is NetworkException -> error.messageId
					is ValidationException -> error.messageId
					is BaseException -> error.messageId.takeIf { it != -1 } ?: R.string.error_unknown
					else -> R.string.error_unknown
				}
				showMessage(messageId)
			}
	}.withProgress(ProgressMode.Filled)

	fun onForgotKeyClick() {
		showMessage(messageString = "Функция в разработке")
	}

	fun onBiometricToggle(enabled: Boolean) {
		_biometricEnabled.value = enabled
	}
}