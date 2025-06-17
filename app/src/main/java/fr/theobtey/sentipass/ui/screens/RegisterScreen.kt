package fr.theobtey.sentipass.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.MaterialTheme

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
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var accountExistsError by remember { mutableStateOf<String?>(null) }
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

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { 
                    username = it
                    accountExistsError = null
                },
                label = { Text("Username", color = Gray) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = accountExistsError != null,
                supportingText = {
                    if (accountExistsError != null) {
                        Text(
                            text = accountExistsError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { 
                    password = it
                    passwordError = null
                    confirmPasswordError = null
                    accountExistsError = null
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                isError = passwordError != null || confirmPasswordError != null || accountExistsError != null,
                supportingText = {
                    if (passwordError != null) {
                        Text(
                            text = passwordError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    } else if (confirmPasswordError != null) {
                        Text(
                            text = confirmPasswordError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    } else if (accountExistsError != null) {
                        Text(
                            text = accountExistsError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { 
                    confirmPassword = it
                    confirmPasswordError = null
                    accountExistsError = null
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                isError = confirmPasswordError != null || accountExistsError != null,
                supportingText = {
                    if (confirmPasswordError != null) {
                        Text(
                            text = confirmPasswordError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    } else if (accountExistsError != null) {
                        Text(
                            text = accountExistsError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (password != confirmPassword) {
                        confirmPasswordError = "Passwords do not match"
                        return@Button
                    }
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
                                // Show success message and navigate to login
                                snackbarHostState.showSnackbar(
                                    message = "Account created successfully! Please login.",
                                    duration = SnackbarDuration.Short
                                )
                                navController.navigate("login") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } else {
                                val errorBody = response.errorBody()?.string()
                                when (response.code()) {
                                    409 -> {
                                        accountExistsError = "This username is already taken"
                                    }
                                    400 -> {
                                        snackbarHostState.showSnackbar(
                                            message = "Missing required fields",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                    else -> {
                                        snackbarHostState.showSnackbar(
                                            message = "Registration failed: ${errorBody ?: "Unknown error"}",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar("Error: ${e.message}")
                        } finally {
                            isLoading = false
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
                        style = TitleTextStyle,
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
