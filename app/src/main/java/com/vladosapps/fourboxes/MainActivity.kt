package com.vladosapps.fourboxes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vladosapps.fourboxes.navigation.NavigationOneRoute
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
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

                }

                val navController = rememberNavController()
                DisposableEffect(key1 = navController) {
                    navigator.setController(navController)
                    onDispose {
                        navigator.clearController()
                    }
                }

                NavHost(navController = navController, startDestination = NavigationOneRoute.route) {

                }
            }
        }
    }
}