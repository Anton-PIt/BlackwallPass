package com.cybersec.blackwallpass.ui.screens.auth.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.cybersec.blackwallpass.ui.theme.LocalAppColors
import com.cybersec.blackwallpass.ui.theme.LocalAppTextStyles

@Composable
internal fun SubmitButton(
	text: String,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	val colors = LocalAppColors.current
	val textStyles = LocalAppTextStyles.current

	val interactionSource = remember { MutableInteractionSource() }
	val isPressed by interactionSource.collectIsPressedAsState()

	val scale by animateFloatAsState(
		targetValue = if (isPressed) 0.98f else 1f,
		animationSpec = tween(durationMillis = 100),
		label = "buttonScale"
	)

	Box(
		modifier = modifier
			.fillMaxWidth()
			.scale(scale)
			.clip(RoundedCornerShape(12.dp))
			.background(
				brush = Brush.linearGradient(colors = listOf(colors.magenta, colors.magentaDark))
			)
			.clickable(
				interactionSource = interactionSource,
				indication = null,
				onClick = onClick
			)
			.padding(vertical = 16.dp),
		contentAlignment = Alignment.Center
	) {
		Text(
			text = text,
			style = textStyles.primaryButton,
			color = colors.white
		)
	}
}