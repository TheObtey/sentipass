package fr.theobtey.sentipass.ui.navigation

import LoginScreen
import fr.theobtey.sentipass.ui.screens.RegisterScreen
import fr.theobtey.sentipass.ui.screens.SettingsScreen
import fr.theobtey.sentipass.ui.screens.ToolsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.theobtey.sentipass.ui.screens.HomeScreen
import java.net.URLDecoder
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = "login",
    onLogin: (String, String) -> Unit,
    onRegister: (String, String) -> Unit
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onLoginClick = { username, password -> onLogin(username, password) },
                onGoToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterClick = { username, password -> onRegister(username, password) },
                onGoToLogin = { navController.popBackStack() }
            )
        }

        composable(
            "home/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("token") ?: ""
            val token = URLDecoder.decode(encoded, "UTF-8")
            HomeScreen(token = token, navController = navController)
        }

        composable(
            "tools/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("token") ?: ""
            val token = URLDecoder.decode(encoded, "UTF-8")
            ToolsScreen(token = token, navController = navController)
        }

        composable(
            "settings/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("token") ?: ""
            val token = URLDecoder.decode(encoded, "UTF-8")
            SettingsScreen(token = token, navController = navController)
        }
    }
}