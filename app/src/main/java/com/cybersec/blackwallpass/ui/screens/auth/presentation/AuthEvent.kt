package com.cybersec.blackwallpass.ui.screens.auth.presentation

sealed interface AuthEvent {
	object CompleteEvent : AuthEvent
}