package com.cybersec.blackwallpass.ui.screens.auth.presentation

import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.core.exception.AuthException
import com.cybersec.blackwallpass.core.exception.BaseException
import com.cybersec.blackwallpass.core.exception.NetworkException
import com.cybersec.blackwallpass.core.exception.ValidationException
import com.cybersec.blackwallpass.ui.common.AppViewModel
import com.cybersec.blackwallpass.ui.common.progress.ProgressMode
import com.cybersec.blackwallpass.ui.screens.auth.domain.AuthValidator
import com.cybersec.blackwallpass.ui.screens.auth.domain.SignInUseCase
import com.cybersec.blackwallpass.ui.screens.auth.domain.SignUpUseCase
import com.cybersec.blackwallpass.ui.screens.auth.model.AuthTab
import com.nulabinc.zxcvbn.Zxcvbn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
	private val signInUseCase: SignInUseCase,
	private val signUpUseCase: SignUpUseCase,
	private val zxcvbn: Zxcvbn,
	private val savedStateHandle: SavedStateHandle
) : AppViewModel<AuthEvent>() {
	private val _tab = mutableStateOf(
		savedStateHandle.get<String>("tab")
			?.let { name -> AuthTab.entries.find { it.name == name } }
			?: AuthTab.LOGIN
	)
	private val _showMasterPassword = mutableStateOf(false)
	private val _showConfirmPassword = mutableStateOf(false)
	private val _biometricAvailable = mutableStateOf(false)
	private val _fingerprintAvailable = mutableStateOf(false)
	private val _faceAvailable = mutableStateOf(false)
	private val _emailError = mutableStateOf<Int?>(null)
	private val _masterPasswordError = mutableStateOf<Int?>(null)
	private val _confirmMasterPasswordError = mutableStateOf<Int?>(null)
	private val _passwordStrength = mutableStateOf<Int?>(null)
	private val fieldsState = AuthFieldsState()

	val uiState = AuthUiState(
		progressState = progressState,
		snackbarState = snackbarState,
		fieldsState = fieldsState,
		passwordStrength = _passwordStrength,
		contentState = AuthContentUiState(
			tab = _tab,
			showMasterPassword = _showMasterPassword,
			showConfirmPassword = _showConfirmPassword,
			biometricAvailable = _biometricAvailable,
			fingerprintAvailable = _fingerprintAvailable,
			faceAvailable = _faceAvailable,
			emailError = _emailError,
			masterPasswordError = _masterPasswordError,
			confirmMasterPasswordError = _confirmMasterPasswordError
		)
	)

	init {
		savedStateHandle.get<String>("email")?.let { email ->
			fieldsState.email.setTextAndPlaceCursorAtEnd(email)
		}
		viewModelScope.launch {
			snapshotFlow { fieldsState.email.text.toString() }
				.collect { savedStateHandle["email"] = it }
		}
		viewModelScope.launch {
			snapshotFlow { fieldsState.masterPassword.text.toString() }
				.distinctUntilChanged()
				.collectLatest { password ->
					delay(300)
					_passwordStrength.value = if (password.isBlank()) {
						null
					} else {
						zxcvbn.measure(password).score
					}
				}
		}
	}

	fun onTabChange(tab: AuthTab) {
		_tab.value = tab
		savedStateHandle["tab"] = tab.name
	}

	fun onShowMasterPasswordToggle(show: Boolean) {
		_showMasterPassword.value = show
	}

	fun onShowConfirmPasswordToggle(show: Boolean) {
		_showConfirmPassword.value = show
	}

	fun onSubmit() = viewModelScope.launch {
		val email = fieldsState.email.text.toString()
		val masterPassword = fieldsState.masterPassword.text.toString()
		val confirmMasterPassword = fieldsState.confirmMasterPassword.text.toString()

		_emailError.value = AuthValidator.validateEmail(email)
		_masterPasswordError.value = AuthValidator.validateMasterPassword(masterPassword)
		_confirmMasterPasswordError.value = if (_tab.value == AuthTab.SIGNUP) {
			AuthValidator.validateConfirmPassword(masterPassword, confirmMasterPassword)
		} else null

		if (_emailError.value != null ||
			_masterPasswordError.value != null ||
			_confirmMasterPasswordError.value != null
		) return@launch

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
}