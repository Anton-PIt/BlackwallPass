package com.cybersec.blackwallpass.ui.common.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cybersec.blackwallpass.R
import com.cybersec.blackwallpass.ui.common.event.OneTimeEventWrapper
import com.cybersec.blackwallpass.ui.common.event.UiEvent
import com.cybersec.blackwallpass.ui.theme.LocalAppColors
import com.cybersec.blackwallpass.ui.theme.LocalAppTextStyles
import kotlinx.coroutines.delay

private const val SNACKBAR_DURATION_MS = 3000L

@Composable
fun AppSnackbar(
	state: OneTimeEventWrapper<UiEvent.SnackbarEvent>,
	modifier: Modifier = Modifier
) {
	val event = state.data

	var visible by remember { mutableStateOf(false) }
	var currentEvent by remember { mutableStateOf<UiEvent.SnackbarEvent?>(null) }

	LaunchedEffect(event) {
		if (event != null) {
			currentEvent = event
			visible = true
			delay(SNACKBAR_DURATION_MS)
			visible = false
		}
	}

	AnimatedVisibility(
		visible = visible,
		enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
		exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
		modifier = modifier
	) {
		currentEvent?.let { snackbarEvent ->
			SnackbarContent(event = snackbarEvent)
		}
	}
}

@Composable
private fun SnackbarContent(event: UiEvent.SnackbarEvent) {
	val colors = LocalAppColors.current
	val textStyles = LocalAppTextStyles.current
	val message = event.messageId?.let { stringResource(it) } ?: event.messageString ?: stringResource(R.string.error_unknown)

	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.padding(horizontal = 16.dp, vertical = 16.dp)
			.fillMaxWidth()
			.clip(RoundedCornerShape(12.dp))
			.background(colors.gray900)
			.border(
				width = 1.dp,
				color = colors.magentaA30,
				shape = RoundedCornerShape(12.dp)
			)
			.padding(16.dp)
	) {
		Box(
			modifier = Modifier
				.width(3.dp)
				.height(20.dp)
				.background(colors.magenta, RoundedCornerShape(2.dp))
		)
		Spacer(modifier = Modifier.width(12.dp))
		Text(
			text = message,
			style = textStyles.snackbarText,
			color = colors.gray100
		)
	}
}