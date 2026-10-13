package com.cybersec.blackwallpass.ui.screens.auth.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import com.cybersec.blackwallpass.ui.common.event.OneTimeEventWrapper
import com.cybersec.blackwallpass.ui.common.event.UiEvent
import com.cybersec.blackwallpass.ui.common.progress.ProgressMode
import com.cybersec.blackwallpass.ui.screens.auth.model.AuthTab

@Immutable
data class AuthUiState(
	val progressState: State<ProgressMode>,
	val snackbarState: State<OneTimeEventWrapper<UiEvent.SnackbarEvent>>,
	val contentState: AuthContentUiState,
	val fieldsState: AuthFieldsState,
	val passwordStrength: State<Int?>
)

@Immutable
data class AuthContentUiState(
	val tab: State<AuthTab>,
	val showMasterPassword: State<Boolean>,
	val showConfirmPassword: State<Boolean>,
	val biometricAvailable: State<Boolean>,
	val fingerprintAvailable: State<Boolean>,
	val faceAvailable: State<Boolean>,
	val emailError: State<Int?>,
	val masterPasswordError: State<Int?>,
	val confirmMasterPasswordError: State<Int?>
)