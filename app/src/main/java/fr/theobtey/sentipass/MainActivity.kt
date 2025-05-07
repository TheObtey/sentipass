package fr.theobtey.sentipass

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import fr.theobtey.sentipass.ui.theme.SentipassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SentipassTheme {
                LoginScreen(
                    onLoginClick = { username, password ->
                        if (username.isBlank() || password.isBlank()) {
                            println("Vous devez remplir les deux champs")
                        } else {
                            println("Login tenté avec : $username / $password")
                            // TODO : fonction de login avec l'API
                        }
                    },
                    onGoToRegister = {
                        println("Vers création de compte")
                    }
                )
            }
        }
    }
}