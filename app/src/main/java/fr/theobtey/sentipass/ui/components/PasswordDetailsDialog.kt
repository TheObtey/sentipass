package fr.theobtey.sentipass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.data.model.PasswordResponse
import fr.theobtey.sentipass.ui.theme.*
import fr.theobtey.sentipass.utils.getPasswordStrength

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordDetailsDialog(
    password: PasswordResponse,
    onClose: () -> Unit,
    onEdit: (PasswordResponse) -> Unit,
    onCopy: (String) -> Unit
) {
    var reveal by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Smoke)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.background(Primary).padding(24.dp)) {

                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = onClose) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = White
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    AsyncImage(
                        model = "https://www.google.com/s2/favicons?sz=64&domain_url=${password.url}",
                        contentDescription = password.service,
                        modifier = Modifier.size(32.dp),
                        error = painterResource(R.drawable.ic_password),
                        fallback = painterResource(R.drawable.ic_password)
                    )

                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = password.service,
                        style = PasswordDetailsTitleStyle,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "edit",
                        style = DefaultTextStyle,
                        color = Gray,
                        modifier = Modifier
                            .clickable { onEdit(password) }
                            .padding(4.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))
                Divider(color = Gray, thickness = 1.dp)
                Spacer(Modifier.height(16.dp))

                // Email / Username
                DetailRow(
                    label = "Email / Username",
                    value = password.email ?: password.username.orEmpty(),
                    onCopy = { onCopy(password.email ?: password.username.orEmpty()) },
                )

                Spacer(Modifier.height(12.dp))

                // Password
                DetailRow(
                    label = "Password",
                    value = if (reveal) password.password else "••••••••••••",
                    onCopy = { onCopy(password.password) },
                    trailingIcon = {
                        IconButton(onClick = { reveal = !reveal }) {
                            Icon(
                                imageVector = if (reveal) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle Reveal"
                            )
                        }
                    }
                )

                Spacer(Modifier.height(12.dp))

                // Password Health
                val (healthText, healthColorRes) = getPasswordStrength(password.password)
                DetailRow(
                    label = "Password Health",
                    value = healthText,
                    valueColor = colorResource(id = healthColorRes)
                )

                Spacer(Modifier.height(12.dp))

                // Website Address
                DetailRow(
                    label = "Website Address",
                    value = password.url.orEmpty(),
                    onCopy = { onCopy(password.url.orEmpty()) },
                    trailingIcon = {
                        IconButton(onClick = {
                            /* TODO: open website */
                        }) {
                            Icon(Icons.Default.OpenInNew, contentDescription = "Open Website")
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = White,
    onCopy: (() -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = PasswordDetailsSubtitleStyle,
            color = Gray,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = PasswordDetailsSubtitleStyle,
            color = valueColor,
            modifier = Modifier.padding(end = 4.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(end = 4.dp)
                .height(24.dp)
        ) {
            onCopy?.let {
                IconButton(
                    onClick = it,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            trailingIcon?.let {
                Box(modifier = Modifier.size(24.dp)) {
                    it()
                }
            }
        }
    }
}