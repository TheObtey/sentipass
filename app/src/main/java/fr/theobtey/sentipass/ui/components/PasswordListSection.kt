package fr.theobtey.sentipass.ui.components

import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.data.model.PasswordResponse
import fr.theobtey.sentipass.ui.theme.DefaultTextStyle
import fr.theobtey.sentipass.ui.theme.Primary
import fr.theobtey.sentipass.ui.theme.White
import fr.theobtey.sentipass.ui.theme.TitleTextStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import fr.theobtey.sentipass.viewmodel.PasswordViewModel
import fr.theobtey.sentipass.viewmodel.AddPasswordState

data class PasswordItem(
    val name: String,
    val username: String
)

@Composable
fun PasswordListSection(
    passwords: List<PasswordResponse>,
    onPasswordClick: (PasswordResponse) -> Unit,
    isCategoryVisible: Boolean,
    viewModel: PasswordViewModel,
    token: String
) {
    var showPopup by remember { mutableStateOf(false) }
    var selectedPassword by remember { mutableStateOf<PasswordResponse?>(null) }
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.home_page_most_recent),
            style = DefaultTextStyle,
            color = White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Box(
            modifier = if (isCategoryVisible)
                Modifier.fillMaxHeight(0.8f).padding(start = 16.dp, end = 16.dp)
            else
                Modifier.fillMaxHeight(0.85f).padding(start = 16.dp, end = 16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp)
            ) {
                items(passwords) { item ->
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                if (isPressed) {
                                    selectedPassword = item
                                    showPopup = true
                                } else {
                                    onPasswordClick(item)
                                }
                            }
                    ) {
                        AsyncImage(
                            model = "https://www.google.com/s2/favicons?sz=64&domain_url=${item.url}",
                            contentDescription = item.service,
                            modifier = Modifier.size(32.dp),
                            error = painterResource(R.drawable.ic_password),
                            fallback = painterResource(R.drawable.ic_password)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = item.service,
                                style = DefaultTextStyle
                            )
                            Text(
                                text = item.email ?: item.username?: "",
                                style = DefaultTextStyle
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    }

    if (showPopup && selectedPassword != null) {
        AlertDialog(
            onDismissRequest = { 
                showPopup = false
                selectedPassword = null
            },
            title = { 
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { 
                            showPopup = false
                            selectedPassword = null
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = selectedPassword!!.service,
                        style = TitleTextStyle,
                        color = White
                    )
                }
            },
            text = { 
                Text(
                    text = "Are you sure you want to delete this password?",
                    style = DefaultTextStyle,
                    color = White
                )
            },
            confirmButton = {
                Button(
                    onClick = { 
                        selectedPassword?.let { password ->
                            viewModel.deletePassword(password.id, token)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("DELETE")
                }
            },
            containerColor = Primary
        )
    }

    LaunchedEffect(state) {
        if (state is AddPasswordState.Success) {
            viewModel.fetchPasswords(token)
            viewModel.resetState()
            showPopup = false
            selectedPassword = null
        }
    }
}