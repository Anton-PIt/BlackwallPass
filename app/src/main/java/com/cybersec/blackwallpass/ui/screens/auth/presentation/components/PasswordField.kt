package com.cybersec.blackwallpass.ui.screens.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.ui.theme.AppColors
import com.cybersec.blackwallpass.ui.theme.AppTextStyles
import com.cybersec.blackwallpass.ui.theme.LocalAppColors
import com.cybersec.blackwallpass.ui.theme.LocalAppTextStyles

@Composable
internal fun PasswordField(
	state: TextFieldState,
	errorState: State<Int?>,
	showPasswordState: State<Boolean>,
	label: String,
	modifier: Modifier = Modifier,
	imeAction: ImeAction = ImeAction.Done,
	onShowPasswordToggle: (Boolean) -> Unit,
	onKeyboardAction: (() -> Unit)? = null
) {
	val colors = LocalAppColors.current
	val textStyles = LocalAppTextStyles.current

	val error by errorState
	val isError = error != null
	val errorMessage = error?.let { stringResource(it) }

	val isErrorState = remember(errorState) {
		derivedStateOf { errorState.value != null }
	}

	Column(modifier = modifier.fillMaxWidth()) {
		Text(
			text = label,
			style = textStyles.fieldLabel,
			color = colors.gray500,
		)
		Spacer(modifier = Modifier.height(6.dp))
		PasswordInput(
			state = state,
			isErrorState = isErrorState,
			showPasswordState = showPasswordState,
			colors = colors,
			textStyles = textStyles,
			imeAction = imeAction,
			onShowPasswordToggle = onShowPasswordToggle,
			onKeyboardAction = onKeyboardAction
		)
		if (isError && errorMessage != null) {
			Spacer(modifier = Modifier.height(4.dp))
			Text(
				text = errorMessage,
				style = textStyles.errorText,
				color = colors.magenta,
			)
		}
	}
}

@Composable
private fun PasswordInput(
	state: TextFieldState,
	isErrorState: State<Boolean>,
	showPasswordState: State<Boolean>,
	colors: AppColors,
	textStyles: AppTextStyles,
	imeAction: ImeAction,
	onShowPasswordToggle: (Boolean) -> Unit,
	onKeyboardAction: (() -> Unit)?
) {
	val isFocused = remember { mutableStateOf(false) }

	val isError by isErrorState
	val showPassword by showPasswordState

	val borderColor = when {
		isError -> colors.magenta
		isFocused.value -> colors.cyanA50
		else -> colors.cyanA30
	}

	BasicSecureTextField(
		state = state,
		textObfuscationMode = if (showPassword) {
			TextObfuscationMode.Visible
		} else {
			TextObfuscationMode.RevealLastTyped
		},
		modifier = Modifier
			.fillMaxWidth()
			.onFocusChanged { isFocused.value = it.isFocused }
			.clip(RoundedCornerShape(8.dp))
			.background(
				if (isError) colors.magenta.copy(alpha = 0.05f)
				else colors.gray900
			)
			.border(
				width = 1.dp,
				color = borderColor,
				shape = RoundedCornerShape(8.dp),
			)
			.padding(horizontal = 16.dp, vertical = 12.dp),
		textStyle = textStyles.fieldValueMono.copy(color = colors.gray100),
		cursorBrush = if (isError) {
			SolidColor(colors.magenta)
		} else {
			SolidColor(colors.cyan)
		},
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Password,
			imeAction = imeAction,
			autoCorrectEnabled = false,
			capitalization = KeyboardCapitalization.None,
		),
		onKeyboardAction = { onKeyboardAction?.invoke() },
		decorator = { innerTextField ->
			Row(
				modifier = Modifier.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
			) {
				Box(modifier = Modifier.weight(1f)) {
					innerTextField()
				}
				Spacer(modifier = Modifier.width(8.dp))
				VisibilityToggle(
					showPasswordState = showPasswordState,
					onToggle = onShowPasswordToggle,
				)
			}
		}
	)
}

@Composable
private fun VisibilityToggle(
	showPasswordState: State<Boolean>,
	onToggle: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
) {
	val colors = LocalAppColors.current
	val showPassword by showPasswordState

	val interactionSource = remember { MutableInteractionSource() }
	val isPressed by interactionSource.collectIsPressedAsState()

	Box(
		modifier = modifier
			.size(24.dp)
			.clip(CircleShape)
			.clickable(
				interactionSource = interactionSource,
				indication = null,
			) {
				onToggle(!showPassword)
			},
		contentAlignment = Alignment.Center,
	) {
		Icon(
			imageVector = if (showPassword) {
				Icons.Outlined.VisibilityOff
			} else {
				Icons.Outlined.Visibility
			},
			contentDescription = stringResource(
				if (showPassword) R.string.cd_hide_password
				else R.string.cd_show_password,
			),
			modifier = Modifier.size(20.dp),
			tint = if (isPressed) colors.cyan else colors.cyanA50,
		)
	}
}