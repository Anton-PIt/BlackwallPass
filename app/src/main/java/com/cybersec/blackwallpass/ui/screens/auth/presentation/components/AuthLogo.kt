package com.cybersec.blackwallpass.ui.screens.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.ui.theme.LocalAppColors
import com.cybersec.blackwallpass.ui.theme.LocalAppTextStyles
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
internal fun AuthLogo(modifier: Modifier = Modifier) {
	val colors = LocalAppColors.current
	val textStyles = LocalAppTextStyles.current

	Column(
		modifier = modifier.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(
			modifier = Modifier
				.size(64.dp)
				.clip(RoundedCornerShape(16.dp))
				.background(
					brush = Brush.linearGradient(
						colors = listOf(
							colors.cyanA15,
							colors.magentaA15
						)
					)
				)
				.border(
					width = 1.dp,
					color = colors.cyanA30,
					shape = RoundedCornerShape(16.dp)
				),
			contentAlignment = Alignment.Center
		) {
			GlitchIcon(
				painter = painterResource(R.drawable.ic_shield),
				size = 32.dp,
			)
		}
		Spacer(modifier = Modifier.height(16.dp))
		Text(
			text = stringResource(R.string.auth_title),
			style = textStyles.appLogoTitle,
			color = colors.cyan,
			textAlign = TextAlign.Center
		)
		Spacer(modifier = Modifier.height(4.dp))
		Text(
			text = stringResource(R.string.auth_subtitle),
			style = textStyles.logoCaption,
			color = colors.gray500,
			textAlign = TextAlign.Center
		)
	}
}

@Composable
private fun GlitchIcon(
	painter: Painter,
	modifier: Modifier = Modifier,
	size: Dp,
) {
	val colors = LocalAppColors.current
	val lifecycleOwner = LocalLifecycleOwner.current

	var offsetX by remember { mutableFloatStateOf(0f) }
	var offsetY by remember { mutableFloatStateOf(0f) }

	var showCyanGhost by remember { mutableStateOf(false) }
	var showMagentaGhost by remember { mutableStateOf(false) }

	LaunchedEffect(lifecycleOwner) {
		lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
			while (true) {
				delay(Random.nextLong(2000, 5000))

				repeat(Random.nextInt(1, 4)) {
					offsetX = Random.nextFloat() * 8f - 4f
					offsetY = Random.nextFloat() * 4f - 2f
					showCyanGhost = Random.nextBoolean()
					showMagentaGhost = Random.nextBoolean()

					delay(Random.nextLong(30, 80))

					offsetX = 0f
					offsetY = 0f
					showCyanGhost = false
					showMagentaGhost = false

					delay(Random.nextLong(30, 100))
				}
			}
		}
	}

	Box(
		modifier = modifier.size(size),
		contentAlignment = Alignment.Center,
	) {
		if (showCyanGhost) {
			Icon(
				painter = painter,
				contentDescription = null,
				tint = colors.cyan.copy(alpha = 0.7f),
				modifier = Modifier
					.size(size)
					.graphicsLayer {
						translationX = -5.dp.toPx()
						translationY = 0f
					}
			)
		}
		if (showMagentaGhost) {
			Icon(
				painter = painter,
				contentDescription = null,
				tint = colors.magenta.copy(alpha = 0.7f),
				modifier = Modifier
					.size(size)
					.graphicsLayer {
						translationX = 5.dp.toPx()
						translationY = 0f
					},
			)
		}
		Icon(
			painter = painter,
			contentDescription = null,
			tint = Color.Unspecified,
			modifier = Modifier
				.size(size)
				.graphicsLayer {
					translationX = offsetX
					translationY = offsetY
				}
		)
	}
}