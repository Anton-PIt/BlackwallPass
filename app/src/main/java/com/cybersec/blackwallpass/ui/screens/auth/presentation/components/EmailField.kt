package com.cybersec.blackwallpass.ui.screens.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cybersec.blackwallpass.ui.theme.AppColors
import com.cybersec.blackwallpass.ui.theme.AppTextStyles
import com.cybersec.blackwallpass.ui.theme.LocalAppColors
import com.cybersec.blackwallpass.ui.theme.LocalAppTextStyles

@Composable
internal fun EmailField(
	state: TextFieldState,
	errorState: State<Int?>,
	label: String,
	modifier: Modifier = Modifier,
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
			color = colors.gray500
		)
		Spacer(modifier = Modifier.height(6.dp))
		EmailInput(
			state = state,
			isErrorState = isErrorState,
			colors = colors,
			textStyles = textStyles,
			onKeyboardAction = onKeyboardAction
		)
		if (isError && errorMessage != null) {
			Spacer(modifier = Modifier.height(4.dp))
			Text(
				text = errorMessage,
				style = textStyles.errorText,
				color = colors.magenta
			)
		}
	}
}

@Composable
private fun EmailInput(
	state: TextFieldState,
	isErrorState: State<Boolean>,
	colors: AppColors,
	textStyles: AppTextStyles,
	onKeyboardAction: (() -> Unit)?
) {
	val isFocused = remember { mutableStateOf(false) }
	val isError by isErrorState

	val borderColor = when {
		isError -> colors.magenta
		isFocused.value -> colors.cyanA50
		else -> colors.cyanA30
	}

	BasicTextField(
		state = state,
		modifier = Modifier
			.fillMaxWidth()
			.onFocusChanged { isFocused.value = it.isFocused }
			.clip(RoundedCornerShape(8.dp))
			.background(
				if (isError) colors.magenta.copy(alpha = 0.05f) else colors.gray900
			)
			.border(
				width = 1.dp,
				color = borderColor,
				shape = RoundedCornerShape(8.dp)
			)
			.padding(horizontal = 16.dp, vertical = 15.dp),
		textStyle = textStyles.fieldValueDefault.copy(color = colors.gray100),
		cursorBrush = if (isError) {
			SolidColor(colors.magenta)
		} else {
			SolidColor(colors.cyan)
		},
		lineLimits = TextFieldLineLimits.SingleLine,
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Email,
			imeAction = ImeAction.Next,
			autoCorrectEnabled = false,
			capitalization = KeyboardCapitalization.None
		),
		onKeyboardAction = { onKeyboardAction?.invoke() }
	)
}