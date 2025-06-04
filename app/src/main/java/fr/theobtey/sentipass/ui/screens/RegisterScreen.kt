package fr.theobtey.sentipass.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.theme.AppNameTextStyle
import fr.theobtey.sentipass.ui.theme.Complementary
import fr.theobtey.sentipass.ui.theme.DefaultTextStyle
import fr.theobtey.sentipass.ui.theme.Primary
import fr.theobtey.sentipass.ui.theme.TitleTextStyle
import fr.theobtey.sentipass.ui.theme.White

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String) -> Unit,
    onGoToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                label = { Text(stringResource(R.string.entry_username), style = DefaultTextStyle) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.entry_password), style = DefaultTextStyle) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = { onRegisterClick(username, password) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Complementary)
            ) {
                Text(
                    text = stringResource(R.string.button_submit),
                    style = TitleTextStyle,
                    color = White
                )
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
