package fr.theobtey.sentipass

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import fr.theobtey.sentipass.ui.theme.SentipassTheme
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import fr.theobtey.sentipass.viewmodel.LoginViewModel
import fr.theobtey.sentipass.viewmodel.LoginState

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SentipassTheme {
                val state by loginViewModel.loginState.collectAsState()

                LoginScreen(
                    onLoginClick = { username, password ->
                        if (username.isBlank() || password.isBlank()) {
                            println("Vous devez remplir les deux champs")
                        } else {
                            println("Login tenté avec : $username / $password")
                            loginViewModel.login(username, password)
                        }
                    },
                    onGoToRegister = {
                        println("Vers création de compte")
                    }
                )

                when (state) {
                    is LoginState.Loading -> println("Chargement...")
                    is LoginState.Success -> {
                        println("Login réussi, token : ${(state as LoginState.Success).token}")
                        loginViewModel.resetState()
                        // TODO : naviguer vers l'écran suivant
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