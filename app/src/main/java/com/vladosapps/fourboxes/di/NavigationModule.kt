package com.vladosapps.fourboxes.di

import com.vladosapps.fourboxes.navigation.Navigator
import com.vladosapps.fourboxes.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    @Singleton
    fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator
}