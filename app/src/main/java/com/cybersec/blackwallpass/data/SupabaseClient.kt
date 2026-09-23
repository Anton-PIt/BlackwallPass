package com.cybersec.blackwallpass.data

import com.cybersec.blackwallpass.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

//Клиент-провайдер для работы с supabase
object SupabaseClientProvider {
	val client: SupabaseClient by lazy {
		createSupabaseClient(
			supabaseUrl = BuildConfig.SUPABASE_URL,
			supabaseKey = BuildConfig.SUPABASE_PUBLISHABLE_KEY
		) {
			install(Auth)
			install(Postgrest)
		}
	}
}