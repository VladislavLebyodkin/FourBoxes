package com.vladosapps.fourboxes.feature.register.presentation

import com.vladosapps.fourboxes.navigation.NavigationRoute

object RegisterRoute : NavigationRoute {
    override fun buildRoute(): String = route

    private const val root = "register"
    const val route = root
}