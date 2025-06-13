package fr.theobtey.sentipass.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.theme.*

@Composable
fun PasswordGeneratorDialog( /* TODO: Improve the UI */
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var password by remember { mutableStateOf("dVX49wB#eq7Vct'SjkCfDKY%GXwrBzjeMJEjCF3xKQ@u2B%Cg%8y2Xk4y'XmQStH") }
    var passwordStrength by remember { mutableStateOf("Strong Password") }
    var passwordLength by remember { mutableStateOf(16f) }
    var useUppercase by remember { mutableStateOf(true) }
    var useDigits by remember { mutableStateOf(true) }
    var useSymbols by remember { mutableStateOf(true) }

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
                .widthIn(min = 320.dp, max = 400.dp)
        ) {
            // Top Row: Close button and title
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = stringResource(R.string.password_generator_title),
                    style = PasswordDetailsTitleStyle
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Password display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = password,
                    style = DefaultTextStyle,
                    color = White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Strength indicator
            Text(
                text = passwordStrength,
                color = when (passwordStrength) {
                    "Weak Password" -> Color.Red
                    "Moderate Password" -> Color.Yellow
                    else -> Color.Green
                },
                style = DefaultTextStyle
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Generate and Copy buttons
            Button(
                onClick = { /* TODO: Generate password logic */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Complementary)
            ) {
                Text(text = stringResource(R.string.password_generator_button_generate), style = TitleTextStyle, color = White)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(password))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Complementary)
            ) {
                Text(text = stringResource(R.string.password_generator_button_copy), style = TitleTextStyle, color = White)
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Parameters
            Text(text = stringResource(R.string.password_generator_password_length), style = DefaultTextStyle, color = White)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Slider(
                    value = passwordLength,
                    onValueChange = { passwordLength = it },
                    valueRange = 8f..64f,
                    steps = 56,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = passwordLength.toInt().toString(), color = White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.password_generator_use_capital_letters), color = White, modifier = Modifier.weight(1f))
                Switch(checked = useUppercase, onCheckedChange = { useUppercase = it })
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.password_generator_use_digits), color = White, modifier = Modifier.weight(1f))
                Switch(checked = useDigits, onCheckedChange = { useDigits = it })
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.password_generator_use_symbols), color = White, modifier = Modifier.weight(1f))
                Switch(checked = useSymbols, onCheckedChange = { useSymbols = it })
            }
        }
    }
} 