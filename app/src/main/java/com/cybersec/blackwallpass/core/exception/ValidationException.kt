package com.cybersec.blackwallpass.core.exception

import androidx.annotation.StringRes

class ValidationException(
	@StringRes messageId: Int,
	message: String? = null
) : BaseException(
	ErrorData(messageId = messageId, message = message)
)