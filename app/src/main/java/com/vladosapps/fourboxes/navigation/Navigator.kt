package com.vladosapps.fourboxes.navigation

import android.os.Parcelable
import androidx.navigation.NavController

interface Navigator {
    fun setController(navController: NavController)
    fun navigate(route: NavigationRoute)
    fun popBackStack()
    fun <T : Parcelable> saveData(data: T, key: String = DEFAULT_KEY)
    fun <T : Parcelable> restoreData(key: String = DEFAULT_KEY): T?
    fun clearController()

    companion object {
        private const val DEFAULT_KEY = "nav_data"
    }
}