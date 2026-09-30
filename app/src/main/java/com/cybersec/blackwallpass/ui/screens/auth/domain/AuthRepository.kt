package com.cybersec.blackwallpass.ui.screens.auth.domain

interface AuthRepository {
	//Регистрация нового пользователя
	suspend fun signUp(email: String, masterPassword: String): Result<Unit>

	//Вход
	suspend fun signIn(email: String, masterPassword: String): Result<Unit>

	//Выход
	suspend fun signOut(): Result<Unit>

	//Получение UUID пользователя
	fun getCurrentUserId(): String?
}