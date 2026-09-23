package com.cybersec.blackwallpass.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//Модель таблицы vault_items
@Serializable
data class VaultItem(
	val id: String? = null,
	@SerialName("user_id") val userId: String,
	val title: String,
	val login: String? = null,
	@SerialName("encrypted_password") val encryptedPassword: String,
	val url: String? = null,
	@SerialName("created_at") val createdAt: String? = null
)