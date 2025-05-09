package fr.theobtey.sentipass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import fr.theobtey.sentipass.ui.navigation.AppNavHost
import fr.theobtey.sentipass.ui.theme.SentipassTheme
import fr.theobtey.sentipass.viewmodel.LoginState
import fr.theobtey.sentipass.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SentipassTheme {
                val navController = rememberNavController()
                val state by loginViewModel.loginState.collectAsState()

                AppNavHost(
                    navController = navController,
                    onLogin = { username, password ->
                        if (username.isBlank() || password.isBlank()) {
                            println("Vous devez remplir les deux champs")
                        } else {
                            println("Login tenté avec : $username / $password")
                            loginViewModel.login(username, password)
                        }
                    }
                )

                when (state) {
                    is LoginState.Loading -> println("Chargement...")
                    is LoginState.Success -> {
                        println("Login réussi, token : ${(state as LoginState.Success).token}")
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true } // empêche retour au login
                        }
                        loginViewModel.resetState()
                    }
                    is LoginState.Error -> {
                        println("Erreur de login : ${(state as LoginState.Error).message}")
                        loginViewModel.resetState()
                    }
                    LoginState.Idle -> {}
                }
            }
        }
    }
}