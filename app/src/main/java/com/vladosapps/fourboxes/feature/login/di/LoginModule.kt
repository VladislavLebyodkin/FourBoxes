package com.vladosapps.fourboxes.feature.login.di

import com.vladosapps.fourboxes.feature.login.data.LoginRepositoryImpl
import com.vladosapps.fourboxes.feature.login.domain.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LoginModule {
    @Binds
    @Singleton
    fun provideLoginRepository(impl: LoginRepositoryImpl): LoginRepository
}