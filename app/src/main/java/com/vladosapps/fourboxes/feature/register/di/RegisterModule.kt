package com.vladosapps.fourboxes.feature.register.di

import com.vladosapps.fourboxes.feature.register.data.RegisterRepositoryImpl
import com.vladosapps.fourboxes.feature.register.domain.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RegisterModule {

    @Binds
    @Singleton
    fun provideRegisterRepository(impl: RegisterRepositoryImpl): RegisterRepository
}