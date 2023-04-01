package com.vladosapps.fourboxes.feature.login.presentation

import androidx.lifecycle.SavedStateHandle
import com.vladosapps.fourboxes.navigation.NavigationRoute

data class LoginRoute(
    val email: String? = null
) : NavigationRoute {
    override fun buildRoute(): String = "$root/$email"

    constructor(savedStateHandle: SavedStateHandle): this(
        email = savedStateHandle.get<String?>(inputArg)
    )

    companion object {
        private const val root = "login"
        private const val inputArg = "email"

        const val route = "$root/{$inputArg}"
    }
}