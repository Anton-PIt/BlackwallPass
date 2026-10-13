package com.cybersec.blackwallpass.ui.screens.auth.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.ui.theme.LocalAppColors
import com.cybersec.blackwallpass.ui.theme.LocalAppTextStyles

@Composable
internal fun SecurityDisclaimer(modifier: Modifier = Modifier) {
	val colors = LocalAppColors.current
	val textStyles = LocalAppTextStyles.current

	Text(
		text = stringResource(R.string.auth_encryption_info),
		modifier = modifier,
		style = textStyles.encryptionInfo,
		color = colors.gray500,
		textAlign = TextAlign.Center,
	)
}