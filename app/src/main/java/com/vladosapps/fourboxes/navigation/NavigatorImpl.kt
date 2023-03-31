package com.vladosapps.fourboxes.navigation

import android.os.Parcelable
import androidx.navigation.NavController
import javax.inject.Inject

class NavigatorImpl @Inject constructor(): Navigator {

    private var navController: NavController? = null

    override fun setController(navController: NavController) {
        this.navController = navController
    }

    override fun navigate(route: NavigationRoute) {
        navController?.navigate(route.buildRoute())
    }

    override fun popBackStack() {
        navController?.popBackStack()
    }

    override fun <T : Parcelable> saveData(data: T, key: String) {
        navController?.previousBackStackEntry?.savedStateHandle?.set(key, data)
    }

    override fun <T : Parcelable> restoreData(key: String): T? {
        return navController?.currentBackStackEntry?.savedStateHandle?.get<T>(key)
    }

    override fun clearController() {
        navController = null
    }
}