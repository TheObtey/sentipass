package fr.theobtey.sentipass.ui.components

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fr.theobtey.sentipass.utils.getPasswordStrength
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.data.model.PasswordRequest
import fr.theobtey.sentipass.ui.theme.*
import fr.theobtey.sentipass.viewmodel.AddPasswordState
import fr.theobtey.sentipass.viewmodel.PasswordViewModel

@Composable
fun AddPasswordDialog(
    onDismiss: () -> Unit,
    viewModel: PasswordViewModel,
    token: String
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    val placeholders = context.resources.getStringArray(R.array.form_fields_defaults)

    var service by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Smoke)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .background(Primary, RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = context.resources.getStringArray(R.array.form_fields_labels)[0],
                    style = PasswordDetailsTitleStyle
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                value = service,
                onValueChange = { service = it },
                placeholder = placeholders[0],
                isRequired = true
            )
            CustomTextField(
                value = url,
                onValueChange = { url = it },
                placeholder = placeholders[1],
                isRequired = false
            )
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = placeholders[3],
                isRequired = false
            )
            CustomTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = placeholders[2],
                isRequired = false
            )
            CustomTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = placeholders[4],
                isRequired = true,
                trailingContent = {
                    val (strengthText, colorRes) = getPasswordStrength(password)
                    val strengthColor = Color(androidx.core.content.ContextCompat.getColor(context, colorRes))
                    Text(text = strengthText, color = strengthColor, style = PasswordDetailsStrengthStyle, modifier = Modifier.padding(end = 8.dp))
                }
            )
            CustomTextField(
                value = note,
                onValueChange = { note = it },
                placeholder = placeholders[5],
                isRequired = false
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (service.isNotBlank() && password.isNotBlank()) {
                        val request = PasswordRequest(
                            service = service,
                            url = url,
                            email = email,
                            username = username,
                            password = password,
                            note = note
                        )

                        viewModel.addPassword(request, token)
                    } else {
                        println("You must fill all required fields")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Complementary)
            ) {
                Text(
                    text = context.resources.getStringArray(R.array.buttons)[5],
                    style = PasswordDetailsTitleStyle
                )
            }
        }
    }

    LaunchedEffect(state) {
        if (state is AddPasswordState.Success) {
            println("Password successfully saved, refreshing the list")
            viewModel.fetchPasswords(token)
            viewModel.resetState()
            onDismiss()
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isRequired: Boolean = false,
    trailingContent: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Row {
                if (isRequired) {
                    Text(text = "*", color = Complementary)
                    Spacer(modifier = Modifier.width(3.dp))
                }
                Text(text = placeholder, style = PasswordDetailsSubtitleStyle)
            }
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        trailingIcon = trailingContent
    )
}