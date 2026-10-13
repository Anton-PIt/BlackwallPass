package com.cybersec.blackwallpass.ui.common.progress

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cybersec.blackwallpass.ui.theme.LocalAppColors

@Composable
fun ProgressBox(
	state: State<ProgressMode>,
	paddingValues: PaddingValues = PaddingValues(0.dp),
) {
	AnimatedContent(
		modifier = Modifier.fillMaxSize(),
		targetState = state.value,
		transitionSpec = { fadeIn() togetherWith fadeOut() }
	) { targetState ->
		when (targetState) {
			ProgressMode.Filled -> DefaultFilled(paddingValues)
			ProgressMode.Overlay -> DefaultOverlay(paddingValues)
			ProgressMode.None -> Box(modifier = Modifier.fillMaxSize())
		}
	}
}

@Composable
private fun DefaultFilled(paddingValues: PaddingValues) {
	val colors = LocalAppColors.current

	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier
			.fillMaxSize()
			.background(colors.gray950.copy(alpha = 0.5f))
			.padding(paddingValues)
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = null
			) {}
	) {
		CircularProgressIndicator(
			strokeWidth = 3.dp,
			color = colors.cyan,
			trackColor = colors.cyanA15,
			modifier = Modifier.size(44.dp)
		)
	}
}

@Composable
private fun DefaultOverlay(paddingValues: PaddingValues) {
	val colors = LocalAppColors.current

	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier
			.fillMaxSize()
			.background(colors.gray950.copy(alpha = 0.85f))
			.padding(paddingValues)
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = null
			) {}
	) {
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.size(64.dp)
				.background(
					color = colors.gray900,
					shape = CircleShape
				)
		) {
			CircularProgressIndicator(
				strokeWidth = 3.dp,
				color = colors.cyan,
				trackColor = colors.cyanA15,
				modifier = Modifier.size(36.dp)
			)
		}
	}
}