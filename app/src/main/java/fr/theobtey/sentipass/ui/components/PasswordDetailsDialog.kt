package fr.theobtey.sentipass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.theobtey.sentipass.data.model.PasswordResponse
import fr.theobtey.sentipass.ui.theme.*

@Composable
fun PasswordDetailsDialog(
    password: PasswordResponse,
    onClose: () -> Unit
) {
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
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = password.service,
                    style = PasswordDetailsTitleStyle,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            password.url?.takeIf { it.isNotBlank() }?.let {
                Text(text = "URL : $it", style = PasswordDetailsSubtitleStyle, color = White)
                Spacer(modifier = Modifier.height(8.dp))
            }

            password.email?.takeIf { it.isNotBlank() }?.let {
                Text(text = "Email : $it", style = PasswordDetailsSubtitleStyle, color = White)
                Spacer(modifier = Modifier.height(8.dp))
            }

            password.username?.takeIf { it.isNotBlank() }?.let {
                Text(text = "Utilisateur : $it", style = PasswordDetailsSubtitleStyle, color = White)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = "Mot de passe : ${password.password}",
                style = PasswordDetailsSubtitleStyle,
                color = Complementary
            )
            Spacer(modifier = Modifier.height(8.dp))

            password.note?.takeIf { it.isNotBlank() }?.let {
                Text(text = "Note : $it", style = PasswordDetailsSubtitleStyle, color = White)
            }
        }
    }
}
