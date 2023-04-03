package com.vladosapps.fourboxes.feature.login.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.vladosapps.fourboxes.navigation.NavigationRoute

data class LoginRoute(
    val email: String = NO_EMAIL
) : NavigationRoute {
    override fun buildRoute(): String = "$root/$email"

    constructor(savedStateHandle: SavedStateHandle) : this(
        email = requireNotNull(savedStateHandle.get<String>(inputArg))
    )

    companion object {
        private const val root = "login"
        private const val inputArg = "email"

        const val route = "$root/{$inputArg}"

        val navArgs = listOf(
            navArgument(name = inputArg) {
                NavType.StringType
                defaultValue = null
                nullable = true
            }
        )

//        TODO избавиться от этого, сделать nullable передаваемый параметр
        const val NO_EMAIL = "no_email"
    }
}