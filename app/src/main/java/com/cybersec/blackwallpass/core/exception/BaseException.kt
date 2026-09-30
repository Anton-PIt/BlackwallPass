package com.cybersec.blackwallpass.core.exception

import androidx.annotation.StringRes

abstract class BaseException private constructor(
	override val message: String?,
	@field:StringRes val messageId: Int
) : Exception() {
	constructor(data: ErrorData) : this(
		message = data.message,
		messageId = data.messageId
	)
}

data class ErrorData(
	val message: String? = null,
	@field:StringRes val messageId: Int = -1
)