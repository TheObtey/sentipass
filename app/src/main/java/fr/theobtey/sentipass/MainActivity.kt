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
import fr.theobtey.sentipass.viewmodel.RegisterState
import fr.theobtey.sentipass.viewmodel.RegisterViewModel
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SentipassTheme {
                val navController = rememberNavController()
                val state by loginViewModel.loginState.collectAsState()
                val registerState by registerViewModel.registerState.collectAsState()

                AppNavHost(
                    navController = navController,
                    onLogin = { username, password ->
                        if (username.isBlank() || password.isBlank()) {
                            println("You must fill all fields")
                        } else {
                            println("Login tried with : $username / $password")
                            loginViewModel.login(username, password)
                        }
                    },
                    onRegister = { username, password ->
                        if (username.isBlank() || password.isBlank()) {
                            println("You must fill all fields")
                        } else {
                            println("Register tried with : $username / $password")
                            registerViewModel.register(username, password)
                        }
                    }
                )

                when (state) {
                    is LoginState.Loading -> println("Loading...")
                    is LoginState.Success -> {
                        val rawToken = (state as LoginState.Success).token
                        val encodedToken = URLEncoder.encode(rawToken, "UTF-8")

                        println("Login successfull, token : $rawToken")

                        navController.navigate("home/$encodedToken") {
                            popUpTo("login") { inclusive = true }
                        }
                        loginViewModel.resetState()
                    }
                    is LoginState.Error -> {
                        println("Login error : ${(state as LoginState.Error).message}")
                        loginViewModel.resetState()
                    }
                    LoginState.Idle -> {}
                }

                when (registerState) {
                    is RegisterState.Loading -> println("Register loading...")
                    is RegisterState.Success -> {
                        println("Register successful")
                        navController.popBackStack()
                        registerViewModel.resetState()
                    }
                    is RegisterState.Error -> {
                        println("Register error : ${(registerState as RegisterState.Error).message}")
                        registerViewModel.resetState()
                    }
                    RegisterState.Idle -> {}
                }
            }
        }
    }
}