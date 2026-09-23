package com.cybersec.blackwallpass.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//Модель таблицы user_security
@Serializable
data class UserSecurity(
	val id: String? = null,
	@SerialName("user_id") val userId: String,
	@SerialName("encrypted_check_string") val encryptedCheckString: String,
	@SerialName("created_at") val createdAt: String? = null,
	val salt: String
)