package com.cybersec.blackwallpass.data.repository

import com.cybersec.blackwallpass.data.model.VaultItem
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

//Репозиторий для работы с данными из таблицы vault_items
class VaultItemsRepository @Inject constructor(
	private val client: SupabaseClient
) {
	//Получить все запись пользователя
	suspend fun getAll(): List<VaultItem> {
		return client.from("vault_items").select().decodeList<VaultItem>()
	}

	//Добавить новую запись
	suspend fun add(item: VaultItem) {
		client.from("vault_items").insert(item)
	}

	//Обновить существующую запись
	suspend fun update(item: VaultItem) {
		val itemId = item.id ?: throw IllegalArgumentException("Cannot update item without id")
		client.from("vault_items").update(
			{
				set("title", item.title)
				set("login", item.login)
				set("encrypted_password", item.encryptedPassword)
				set("url", item.url)
			}
		) {
			filter { eq("id", itemId) }
		}
	}

	//Удаление записи по id
	suspend fun delete(id: String) {
		client.from("vault_items").delete {
			filter { eq("id", id) }
		}
	}
}