package com.cybersec.blackwallpass.core.exception

import androidx.annotation.StringRes

//Ошибка сети
class NetworkException(
	@StringRes messageId: Int,
	message: String? = null
) : BaseException(
	ErrorData(messageId = messageId, message = message)
)