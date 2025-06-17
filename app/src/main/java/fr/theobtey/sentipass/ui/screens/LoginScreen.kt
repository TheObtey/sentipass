import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.theme.*
import fr.theobtey.sentipass.viewmodel.LoginState
import fr.theobtey.sentipass.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onGoToRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val loginViewModel: LoginViewModel = viewModel()
    val loginState by loginViewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Loading -> {
                isLoading = true
            }
            is LoginState.Success -> {
                isLoading = false
                val token = (loginState as LoginState.Success).token
                onLoginClick(username, password)
                loginViewModel.resetState()
            }
            is LoginState.Error -> {
                isLoading = false
                val errorMessage = (loginState as LoginState.Error).message
                when {
                    errorMessage.contains("Username not found") -> {
                        usernameError = errorMessage
                        passwordError = null
                    }
                    errorMessage.contains("Incorrect password") -> {
                        passwordError = errorMessage
                        usernameError = null
                    }
                    else -> {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = errorMessage,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
                loginViewModel.resetState()
            }
            LoginState.Idle -> {
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
                    usernameError = null
                },
                label = { Text(stringResource(R.string.entry_username), style = DefaultTextStyle) },
                singleLine = true,
                isError = usernameError != null,
                supportingText = {
                    if (usernameError != null) {
                        Text(
                            text = usernameError!!,
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

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {
                    if (username.isBlank() || password.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Please fill all fields",
                                duration = SnackbarDuration.Short
                            )
                        }
                        return@Button
                    }
                    loginViewModel.login(username, password)
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
                        text = stringResource(R.string.button_submit),
                        style = TitleTextStyle,
                        color = White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onGoToRegister) {
                Text(
                    text = stringResource(R.string.button_create_new_account),
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