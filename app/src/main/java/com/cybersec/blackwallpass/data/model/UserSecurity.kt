package com.cybersec.blackwallpass.data.model

import com.cybersec.blackwallpass.data.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

//Модель таблицы user_security
@Serializable
data class UserSecurity(
	val id: String? = null,
	@SerialName("user_id") val userId: String,
	@SerialName("encrypted_check_string") val encryptedCheckString: String,
	@SerialName("created_at") @Serializable(with = InstantSerializer::class) val createdAt: Instant? = null,
	val salt: String
)