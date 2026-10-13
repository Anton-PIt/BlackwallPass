package com.cybersec.blackwallpass.di

import com.nulabinc.zxcvbn.Zxcvbn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ZxcvbnModule {

	@Provides
	@Singleton
	fun provideZxcvbn(): Zxcvbn = Zxcvbn()
}