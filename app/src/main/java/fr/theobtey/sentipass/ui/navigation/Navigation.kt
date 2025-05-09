package fr.theobtey.sentipass.ui.navigation

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.theobtey.sentipass.ui.screens.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = "login",
    onLogin: (String, String) -> Unit
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onLoginClick = { username, password -> onLogin(username, password) },
                onGoToRegister = {  }
            )
        }

        composable("home") {
            HomeScreen()
        }
    }
}