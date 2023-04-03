package com.vladosapps.fourboxes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vladosapps.fourboxes.feature.login.presentation.LoginRoute
import com.vladosapps.fourboxes.feature.login.presentation.LoginScreen
import com.vladosapps.fourboxes.feature.register.presentation.RegisterRoute
import com.vladosapps.fourboxes.feature.register.presentation.RegisterScreen
import com.vladosapps.fourboxes.navigation.Navigator
import com.vladosapps.fourboxes.ui.theme.FourBoxesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FourBoxesTheme {
                val navController = rememberNavController()
                DisposableEffect(key1 = navController) {
                    navigator.setController(navController)
                    onDispose { navigator.clearController() }
                }

                NavHost(
                    navController = navController,
                    startDestination = RegisterRoute.route
//                    startDestination = LoginRoute.route
                ) {
                    composable(route = RegisterRoute.route) {
                        RegisterScreen()
                    }
                    composable(route = LoginRoute.route, arguments = LoginRoute.navArgs) {
                        LoginScreen()
                    }
                }
            }
        }
    }
}