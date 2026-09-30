package com.cybersec.blackwallpass.core.exception

import androidx.annotation.StringRes

//Неизвестная ошибка
class UnknownException(
	@StringRes messageId: Int,
	message: String? = null
) : BaseException(
	ErrorData(messageId = messageId, message = message)
)