package com.cybersec.blackwallpass.ui.screens.auth.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import com.cybersec.blackwallpass.ui.common.OneTimeEventWrapper
import com.cybersec.blackwallpass.ui.common.ProgressMode
import com.cybersec.blackwallpass.ui.common.UiEvent
import com.cybersec.blackwallpass.ui.screens.auth.model.AuthTab
import com.cybersec.blackwallpass.ui.screens.auth.model.BiometricType

@Immutable
data class AuthUiState(
	val progressState: State<ProgressMode>,
	val snackbarState: State<OneTimeEventWrapper<UiEvent.SnackbarEvent>>,
	val contentState: AuthContentUiState
)

@Immutable
data class AuthContentUiState(
	val tab: State<AuthTab>,
	val email: State<String>,
	val masterPassword: State<String>,
	val confirmMasterPassword: State<String>,
	val showPassword: State<Boolean>,
	val biometricEnabled: State<Boolean>,
	val biometricType: State<BiometricType>,
	val biometricAvailable: State<Boolean>,
	val fingerprintAvailable: State<Boolean>,
	val faceAvailable: State<Boolean>
)