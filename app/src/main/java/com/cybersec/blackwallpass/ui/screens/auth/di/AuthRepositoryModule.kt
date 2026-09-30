package com.cybersec.blackwallpass.ui.screens.auth.di

import com.cybersec.blackwallpass.ui.screens.auth.data.AuthRepositoryImpl
import com.cybersec.blackwallpass.ui.screens.auth.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
	@Binds
	abstract fun bindAuthRepository(source: AuthRepositoryImpl): AuthRepository
}