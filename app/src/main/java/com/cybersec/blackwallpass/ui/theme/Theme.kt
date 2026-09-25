package com.cybersec.blackwallpass.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
	primary = Color(0xFF00F0FF),
	secondary = Color(0xFFFF0055),
	background = Color(0xFF0A0A0C),
	surface = Color(0xFF111116),
	onPrimary = Color(0xFF0A0A0C),
	onSecondary = Color(0xFFFFFFFF),
	onBackground = Color(0xFFE8E8F0),
	onSurface = Color(0xFFE8E8F0)
)

@Composable
fun BlackwallPassTheme(content: @Composable () -> Unit) {
	CompositionLocalProvider(LocalAppColors provides AppColors()) {
		MaterialTheme(
			colorScheme = DarkColorScheme,
			content = content
		)
	}
}