package com.cybersec.blackwallpass.ui.screens.auth.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cybersec.blackwallpass.ui.theme.LocalAppColors
import com.cybersec.blackwallpass.ui.theme.LocalAppTextStyles

@Composable
internal fun AuthTabButton(
	text: String,
	isActive: Boolean,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	val colors = LocalAppColors.current
	val textStyles = LocalAppTextStyles.current

	val textColor by animateColorAsState(
		targetValue = if (isActive) colors.cyan else colors.gray500,
		animationSpec = tween(durationMillis = 200),
		label = "tabTextColor"
	)
	val backgroundAlpha by animateFloatAsState(
		targetValue = if (isActive) 1f else 0f,
		animationSpec = tween(durationMillis = 200),
		label = "tabBackgroundAlpha"
	)

	Box(
		modifier = modifier
			.clip(RoundedCornerShape(8.dp))
			.background(
				color = if (isActive) {
					colors.cyan.copy(alpha = 0.15f * backgroundAlpha)
				} else
					Color.Transparent
			)
			.border(
				width = 1.dp,
				color = if (isActive) {
					colors.cyanA30.copy(alpha = backgroundAlpha)
				} else
					Color.Transparent,
				shape = RoundedCornerShape(8.dp)
			)
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = null,
				onClick = onClick
			)
			.padding(vertical = 10.dp),
		contentAlignment = Alignment.Center
	) {
		Text(
			text = text,
			style = textStyles.tabLabel,
			color = textColor
		)
	}
}