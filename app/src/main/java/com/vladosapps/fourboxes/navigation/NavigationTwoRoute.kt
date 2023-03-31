package com.vladosapps.fourboxes.navigation

import androidx.lifecycle.SavedStateHandle

data class NavigationTwoRoute(val value: String) : NavigationRoute {

//    constructor(savedStateHandle: SavedStateHandle): this(
//        value = requireNotNull(savedStateHandle.get<String>(inputArg))
//    )

    override fun buildRoute(): String = "$root/$value"

    companion object {
        private const val root = "two"
        private const val inputArg = "input"

        const val route = "$root/{$inputArg}"
    }
}