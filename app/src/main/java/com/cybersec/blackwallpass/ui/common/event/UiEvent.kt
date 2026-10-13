package com.cybersec.blackwallpass.ui.common.event

sealed interface UiEvent {
	data class SnackbarEvent(
		val messageId: Int?,
		val messageString: String?
	) : UiEvent
}