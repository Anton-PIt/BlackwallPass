package com.cybersec.blackwallpass.ui.screens.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleStartEffect
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.ui.common.components.AppSnackbar
import com.cybersec.blackwallpass.ui.common.progress.ProgressBox
import com.cybersec.blackwallpass.ui.screens.auth.model.AuthTab
import com.cybersec.blackwallpass.ui.screens.auth.presentation.components.*
import com.cybersec.blackwallpass.ui.theme.LocalAppColors

@Composable
internal fun AuthScreen(
	viewModel: AuthViewModel
) {
	AuthScreen(
		state = viewModel.uiState,
		onTabChange = viewModel::onTabChange,
		onShowMasterPasswordToggle = viewModel::onShowMasterPasswordToggle,
		onShowConfirmPasswordToggle = viewModel::onShowConfirmPasswordToggle,
		onForgotKeyClick = viewModel::onForgotKeyClick,
		onSubmit = viewModel::onSubmit
	)
	LifecycleStartEffect(Unit) {
		viewModel.onStart()
		onStopOrDispose { }
	}
}

@Composable
private fun AuthScreen(
	state: AuthUiState,
	onTabChange: (AuthTab) -> Unit,
	onShowMasterPasswordToggle: (Boolean) -> Unit,
	onShowConfirmPasswordToggle: (Boolean) -> Unit,
	onForgotKeyClick: () -> Unit,
	onSubmit: () -> Unit
) {
	Scaffold {
		Box(modifier = Modifier.padding(it)) {
			AuthContent(
				state = state.contentState,
				fieldsState = state.fieldsState,
				passwordStrengthState = state.passwordStrength,
				onTabChange = onTabChange,
				onShowMasterPasswordToggle = onShowMasterPasswordToggle,
				onShowConfirmPasswordToggle = onShowConfirmPasswordToggle,
				onForgotKeyClick = onForgotKeyClick,
				onSubmit = onSubmit
			)
			ProgressBox(state = state.progressState)
			AppSnackbar(
				state = state.snackbarState.value,
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.fillMaxWidth()
			)
		}
	}
}

@Composable
private fun AuthContent(
	state: AuthContentUiState,
	fieldsState: AuthFieldsState,
	passwordStrengthState: State<Int?>,
	onTabChange: (AuthTab) -> Unit,
	onShowMasterPasswordToggle: (Boolean) -> Unit,
	onShowConfirmPasswordToggle: (Boolean) -> Unit,
	onForgotKeyClick: () -> Unit,
	onSubmit: () -> Unit
) {
	val focusManager = LocalFocusManager.current
	val colors = LocalAppColors.current
	val tab by state.tab

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier
			.fillMaxSize()
			.background(colors.gray950)
			.verticalScroll(rememberScrollState())
			.imePadding()
			.padding(horizontal = 24.dp)
			.padding(top = 16.dp, bottom = 32.dp)
	) {
		Spacer(modifier = Modifier.height(24.dp))
		AuthLogo()
		Spacer(modifier = Modifier.height(32.dp))
		AuthTabs(
			selectedTab = tab,
			onTabChange = onTabChange
		)
		Spacer(modifier = Modifier.height(24.dp))
		EmailField(
			state = fieldsState.email,
			label = stringResource(R.string.auth_field_email),
			errorState = state.emailError,
			onKeyboardAction = { focusManager.moveFocus(FocusDirection.Down) }
		)
		Spacer(modifier = Modifier.height(16.dp))
		PasswordField(
			state = fieldsState.masterPassword,
			errorState = state.masterPasswordError,
			showPasswordState = state.showMasterPassword,
			label = stringResource(R.string.auth_field_master_password),
			imeAction = ImeAction.Next,
			onShowPasswordToggle = onShowMasterPasswordToggle,
			onKeyboardAction = {
				if (tab == AuthTab.LOGIN) {
					focusManager.clearFocus()
				} else {
					focusManager.moveFocus(FocusDirection.Down)
				}
			}
		)
		if (tab == AuthTab.SIGNUP) {
			Spacer(modifier = Modifier.height(16.dp))
			PasswordField(
				state = fieldsState.confirmMasterPassword,
				errorState = state.confirmMasterPasswordError,
				showPasswordState = state.showConfirmPassword,
				label = stringResource(R.string.auth_field_confirm_password),
				onShowPasswordToggle = onShowConfirmPasswordToggle,
				onKeyboardAction = { focusManager.clearFocus() }
			)
			Spacer(modifier = Modifier.height(8.dp))
			PasswordStrengthIndicator(scoreState = passwordStrengthState)
		}
		if (tab == AuthTab.LOGIN) {
			Spacer(modifier = Modifier.height(12.dp))
			ForgotKeyLink(onClick = onForgotKeyClick)
		}
		Spacer(modifier = Modifier.height(24.dp))
		SubmitButton(
			text = if (tab == AuthTab.LOGIN) {
				stringResource(R.string.auth_button_login)
			} else {
				stringResource(R.string.auth_button_signup)
			},
			onClick = onSubmit
		)
		Spacer(modifier = Modifier.height(16.dp))
		SecurityDisclaimer()
	}
}