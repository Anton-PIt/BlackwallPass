package com.cybersec.blackwallpass.core.exception

import androidx.annotation.StringRes

//Ошибка авторизации
class AuthException(
	@StringRes messageId: Int,
	message: String? = null
) : BaseException(
	ErrorData(messageId = messageId, message = message)
)