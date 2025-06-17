package fr.theobtey.sentipass.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.theme.AppNameTextStyle
import fr.theobtey.sentipass.ui.theme.Complementary
import fr.theobtey.sentipass.ui.theme.DefaultTextStyle
import fr.theobtey.sentipass.ui.theme.Gray
import fr.theobtey.sentipass.ui.theme.Primary
import fr.theobtey.sentipass.ui.theme.Red
import fr.theobtey.sentipass.ui.theme.TitleTextStyle
import fr.theobtey.sentipass.ui.theme.White
import androidx.compose.material3.TextFieldDefaults
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.compose.material3.CircularProgressIndicator
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberCoroutineScope
import fr.theobtey.sentipass.data.network.RetrofitClient
import fr.theobtey.sentipass.data.model.RegisterRequest
import fr.theobtey.sentipass.data.model.LoginResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterClick: (String, String) -> Unit,
    onGoToLogin: () -> Unit,
    navController: NavController
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPasswordMismatchError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageModifier = Modifier.size(250.dp)

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Sentipass logo",
                modifier = imageModifier
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = stringResource(R.string.app_name),
                style = AppNameTextStyle
            )

            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Gray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    cursorColor = White,
                    focusedBorderColor = Complementary,
                    unfocusedBorderColor = Gray,
                    focusedLabelColor = Gray,
                    unfocusedLabelColor = Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { 
                    password = it
                    showPasswordMismatchError = false
                },
                label = { Text("Password", color = Gray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    cursorColor = White,
                    focusedBorderColor = Complementary,
                    unfocusedBorderColor = Gray,
                    focusedLabelColor = Gray,
                    unfocusedLabelColor = Gray
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { 
                    confirmPassword = it
                    showPasswordMismatchError = false
                },
                label = { Text("Confirm Password", color = Gray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    cursorColor = White,
                    focusedBorderColor = Complementary,
                    unfocusedBorderColor = Gray,
                    focusedLabelColor = Gray,
                    unfocusedLabelColor = Gray
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            if (showPasswordMismatchError) {
                Text(
                    text = "Passwords do not match",
                    color = Red,
                    style = DefaultTextStyle,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {
                    if (password != confirmPassword) {
                        showPasswordMismatchError = true
                    } else {
                        coroutineScope.launch {
                            isLoading = true
                            try {
                                val response = RetrofitClient.api.register(
                                    request = RegisterRequest(
                                        username = username,
                                        password = password
                                    )
                                )
                                
                                if (response.isSuccessful) {
                                    // Store token
                                    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
                                    val sharedPreferences = EncryptedSharedPreferences.create(
                                        "sentipass_prefs",
                                        masterKeyAlias,
                                        context,
                                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                                    )
                                    val token = "Bearer ${response.body()}"
                                    sharedPreferences.edit()
                                        .putString("token", token)
                                        .apply()
                                    
                                    // Navigate to home
                                    navController.navigate("home/$token") {
                                        popUpTo(0) { inclusive = true }
                                    }
                                } else {
                                    snackbarHostState.showSnackbar("Registration failed: ${response.message()}")
                                }
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error: ${e.message}")
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Complementary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "GET STARTED",
                        color = White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onGoToLogin) {
                Text(
                    text = stringResource(R.string.button_existing_account),
                    style = DefaultTextStyle
                )
            }
        }
    }
}
