package com.cybersec.blackwallpass.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
	// Фон
	val bg: Color = Color(0xFF0A0A0C),
	val surface: Color = Color(0xFF111116),
	val surface2: Color = Color(0xFF16161D),
	val border: Color = Color(0xFF1E1E2A),

	// Акценты
	val cyan: Color = Color(0xFF00F0FF),
	val magenta: Color = Color(0xFFFF0055),

	// Текст
	val text: Color = Color(0xFFE8E8F0),
	val muted: Color = Color(0xFF6B6B80),

	// Прозрачные варианты
	val cyanA15: Color = Color(0x2600F0FF),
	val cyanA30: Color = Color(0x4D00F0FF),
	val cyanA50: Color = Color(0x8000F0FF),
	val magentaA15: Color = Color(0x26FF0055),
	val magentaA30: Color = Color(0x4DFF0055),
	val magentaA50: Color = Color(0x80FF0055),
	val borderA20: Color = Color(0x331E1E2A),
	val textA80: Color = Color(0xCCE8E8F0),
)