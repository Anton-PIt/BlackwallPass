package com.cybersec.blackwallpass.ui.screens.auth.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable

@Stable
class AuthFieldsState {
	val email = TextFieldState()
	val masterPassword = TextFieldState()
	val confirmMasterPassword = TextFieldState()
}