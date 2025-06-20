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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.material3.SnackbarHost
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import fr.theobtey.sentipass.viewmodel.RegisterState
import fr.theobtey.sentipass.viewmodel.RegisterViewModel

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
    val registerViewModel: RegisterViewModel = viewModel()
    val registerState by registerViewModel.registerState.collectAsState()

    LaunchedEffect(registerState) {
        when (registerState) {
            is RegisterState.Loading -> {
                isLoading = true
            }
            is RegisterState.Success -> {
                isLoading = false
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Account created successfully! Please login.",
                        duration = SnackbarDuration.Short
                    )
                }
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true }
                }
                registerViewModel.resetState()
            }
            is RegisterState.Error -> {
                isLoading = false
                val errorMessage = (registerState as RegisterState.Error).message
                if (errorMessage.contains("Account already exists")) {
                    accountExistsError = errorMessage
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = errorMessage,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
                registerViewModel.resetState()
            }
            RegisterState.Idle -> {
                isLoading = false
            }
        }
    }

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
                onValueChange = { 
                    username = it
                    accountExistsError = null
                },
                label = { Text(stringResource(R.string.entry_username), style = DefaultTextStyle) },
                singleLine = true,
                isError = accountExistsError != null,
                supportingText = {
                    if (accountExistsError != null) {
                        Text(
                            text = accountExistsError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { 
                    password = it
                    passwordError = null
                },
                label = { Text(stringResource(R.string.entry_password), style = DefaultTextStyle) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError != null,
                supportingText = {
                    if (passwordError != null) {
                        Text(
                            text = passwordError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { 
                    confirmPassword = it
                    confirmPasswordError = null
                },
                label = { Text(stringResource(R.string.entry_password), style = DefaultTextStyle) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = confirmPasswordError != null,
                supportingText = {
                    if (confirmPasswordError != null) {
                        Text(
                            text = confirmPasswordError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {
                    if (password != confirmPassword) {
                        confirmPasswordError = "Passwords do not match"
                        return@Button
                    }
                    if (username.isBlank() || password.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Please fill all fields",
                                duration = SnackbarDuration.Short
                            )
                        }
                        return@Button
                    }
                    onRegisterClick(username, password)
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
                        text = stringResource(R.string.button_get_started),
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

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}