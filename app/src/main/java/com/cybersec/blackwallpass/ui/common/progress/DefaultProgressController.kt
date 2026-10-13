package com.cybersec.blackwallpass.ui.common.progress

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DefaultProgressController(
	private val scope: CoroutineScope
) {
	private val _progressState = mutableStateOf<ProgressMode>(ProgressMode.None)
	val progressState: State<ProgressMode> get() = _progressState

	fun show(mode: ProgressMode = ProgressMode.Filled) {
		_progressState.value = mode
	}

	fun hide() {
		_progressState.value = ProgressMode.None
	}

	fun <T : Job> T.withProgress(
		mode: ProgressMode = ProgressMode.Filled,
		delayMs: Long = 0L
	): T {
		scope.launch {
			if (delayMs > 0) delay(delayMs)
			show(mode)
		}
		invokeOnCompletion { hide() }
		return this
	}

	fun <T> Flow<T>.withProgress(
		mode: ProgressMode = ProgressMode.Filled,
		delayMs: Long = 0L
	): Flow<T> {
		return onStart {
			if (delayMs > 0) delay(delayMs)
			show(mode)
		}.onCompletion { hide() }
	}
}