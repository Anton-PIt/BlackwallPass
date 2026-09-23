package com.cybersec.blackwallpass.data.repository

import com.cybersec.blackwallpass.data.SupabaseClientProvider
import com.cybersec.blackwallpass.data.model.UserSecurity
import io.github.jan.supabase.postgrest.from

//Репозиторий для работы с данными из user_security
class UserSecurityRepository {
	private val client = SupabaseClientProvider.client

	//Метод сохранеиня пользователя в бд
	suspend fun save(record: UserSecurity) {
		client.from("user_security").insert(record)
	}

	//Метод, который возвращает данные пользователя его по id
	suspend fun getByUserId(userId: String): UserSecurity? {
		return client.from("user_security").select {
			filter { eq("user_id", userId) }
		}.decodeSingleOrNull<UserSecurity>()
	}

	//Метод изменения контрольной строки
	suspend fun updateCheckString(userId: String, newCheckString: String) {
		client.from("user_security").update({ set("encrypted_check_string", newCheckString) }) {
			filter {
				eq("user_id", userId)
			}
		}
	}
}