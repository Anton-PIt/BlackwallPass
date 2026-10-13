package com.cybersec.blackwallpass.ui.screens.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.ui.theme.LocalAppColors
import com.cybersec.blackwallpass.ui.theme.LocalAppTextStyles

@Composable
internal fun PasswordStrengthIndicator(
	scoreState: State<Int?>,
	modifier: Modifier = Modifier
) {
	val score by scoreState
	val currentScore = score ?: return

	val colors = LocalAppColors.current
	val textStyles = LocalAppTextStyles.current

	val (labelRes, barColor) = when (currentScore) {
		0, 1 -> R.string.password_strength_weak to colors.magenta
		2 -> R.string.password_strength_medium to colors.magenta
		3 -> R.string.password_strength_strong to colors.cyan
		else -> R.string.password_strength_max to colors.cyan
	}

	Column(modifier = modifier.fillMaxWidth()) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(4.dp)
		) {
			repeat(4) { index ->
				val active = index < currentScore
				Spacer(
					modifier = Modifier
						.weight(1f)
						.height(4.dp)
						.clip(RoundedCornerShape(2.dp))
						.background(if (active) barColor else colors.gray700)
				)
			}
		}
		Spacer(modifier = Modifier.height(4.dp))
		Text(
			text = stringResource(labelRes),
			style = textStyles.logoCaption,
			color = colors.gray500
		)
	}
}