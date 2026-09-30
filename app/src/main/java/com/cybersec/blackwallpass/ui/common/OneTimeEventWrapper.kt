package com.cybersec.blackwallpass.ui.common

import androidx.compose.runtime.Stable

//Обёртка для одноразовых событий
@Stable
class OneTimeEventWrapper<T>(data: T?) {
	private var received: Boolean = false

	val data: T? = data
		get() = if (!received) {
			received = true
			field
		} else null
}