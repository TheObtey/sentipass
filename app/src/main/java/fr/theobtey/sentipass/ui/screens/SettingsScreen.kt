package fr.theobtey.sentipass.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.components.*
import fr.theobtey.sentipass.ui.theme.*
import androidx.navigation.NavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import fr.theobtey.sentipass.data.network.RetrofitClient

@Composable
fun SettingsScreen(
    token: String,
    navController: NavController
) {
    var showDisconnectDialog by remember { mutableStateOf(false) }
    var showDeleteAllPasswordsDialog by remember { mutableStateOf(false) }
    var showNukeDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HeaderSection()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_title),
                style = DefaultTextStyle,
                color = White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SettingsButton(
                        title = stringResource(R.string.settings_change_password),
                        description = stringResource(R.string.settings_change_password_description),
                        onClick = { showChangePasswordDialog = true },
                        isDestructive = false
                    )
                }
                item {
                    SettingsButton(
                        title = stringResource(R.string.settings_disconnect),
                        description = stringResource(R.string.settings_disconnect_description),
                        onClick = { showDisconnectDialog = true },
                        isDestructive = true
                    )
                }
                item {
                    SettingsButton(
                        title = stringResource(R.string.settings_delete_all_passwords),
                        description = stringResource(R.string.settings_delete_all_passwords_description),
                        onClick = { showDeleteAllPasswordsDialog = true },
                        isDestructive = true
                    )
                }
                item {
                    SettingsButton(
                        title = stringResource(R.string.settings_nuke),
                        description = stringResource(R.string.settings_nuke_description),
                        onClick = { showNukeDialog = true },
                        isDestructive = true
                    )
                }
            }
        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            navController = navController,
            currentRoute = "settings/$token"
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        if (showDisconnectDialog) {
            DisconnectDialog(
                onDismiss = { showDisconnectDialog = false },
                onDisconnect = {
                    // Remove all data from EncryptedSharedPreferences
                    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
                    val sharedPreferences = EncryptedSharedPreferences.create(
                        "sentipass_prefs",
                        masterKeyAlias,
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    )
                    sharedPreferences.edit().clear().apply()
                    // Navigate to login
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        if (showDeleteAllPasswordsDialog) {
            DeleteAllPasswordsDialog(
                onDismiss = { showDeleteAllPasswordsDialog = false },
                onConfirm = {
                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.api.deleteAllPasswords(
                                token = "Bearer $token"
                            )
                            
                            showDeleteAllPasswordsDialog = false
                            
                            if (response.isSuccessful) {
                                snackbarHostState.showSnackbar("All passwords deleted successfully")
                            } else {
                                snackbarHostState.showSnackbar("Failed to delete passwords: ${response.message()}")
                            }
                        } catch (e: Exception) {
                            showDeleteAllPasswordsDialog = false
                            snackbarHostState.showSnackbar("Error: ${e.message}")
                        }
                    }
                }
            )
        }

        if (showNukeDialog) {
            NukeDialog(
                onDismiss = { showNukeDialog = false },
                onConfirm = {
                    // TODO: Implement nuke functionality
                    // 1. Delete all passwords
                    // 2. Delete account from database
                    // 3. Clear local data
                    // 4. Navigate to login
                    println("Nuke confirmed")
                    showNukeDialog = false
                }
            )
        }

        if (showChangePasswordDialog) {
            ChangePasswordDialog(
                onDismiss = { showChangePasswordDialog = false },
                onConfirm = { currentPassword, newPassword, confirmPassword ->
                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.api.updateMasterPassword(
                                token = "Bearer $token",
                                request = mapOf(
                                    "oldPassword" to currentPassword,
                                    "newPassword" to newPassword
                                )
                            )
                            
                            showChangePasswordDialog = false
                
                            if (response.isSuccessful) {
                                snackbarHostState.showSnackbar("Password updated successfully")
                            } else {
                                snackbarHostState.showSnackbar("Failed to update password: ${response.message()}")
                            }
                        } catch (e: Exception) {
                            showChangePasswordDialog = false
                            snackbarHostState.showSnackbar("Error: ${e.message}")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun DeleteAllPasswordsDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99000000))
            .clickable(enabled = false) { }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Primary)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.settings_delete_all_passwords_confirmation_title),
                    style = DefaultTextStyle,
                    color = White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = stringResource(R.string.settings_delete_all_passwords_confirmation_message),
                    style = DefaultTextStyle,
                    color = Gray,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.dialog_confirm),
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(R.string.dialog_cancel),
                        color = Complementary
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsButton(
    title: String,
    description: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDestructive) Red else Complementary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = DefaultTextStyle,
                color = White,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = DefaultTextStyle,
                color = Gray
            )
        }
    }
}

@Composable
fun DisconnectDialog(
    onDismiss: () -> Unit,
    onDisconnect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99000000))
            .clickable(enabled = false) { }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Primary)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.settings_disconnect_confirmation_title),
                    style = DefaultTextStyle,
                    color = White,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Button(
                    onClick = onDisconnect,
                    colors = ButtonDefaults.buttonColors(containerColor = Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.dialog_confirm),
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(R.string.dialog_cancel),
                        color = Complementary
                    )
                }
            }
        }
    }
}

@Composable
fun NukeDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99000000))
            .clickable(enabled = false) { }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Primary)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.settings_nuke_confirmation_title),
                    style = DefaultTextStyle,
                    color = White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = stringResource(R.string.settings_nuke_confirmation_message),
                    style = DefaultTextStyle,
                    color = Gray,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.dialog_confirm),
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(R.string.dialog_cancel),
                        color = Complementary
                    )
                }
            }
        }
    }
}

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (currentPassword: String, newPassword: String, confirmPassword: String) -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPasswordMismatchError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99000000))
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Primary)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.settings_change_password_confirmation_title),
                    style = DefaultTextStyle,
                    color = White,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = { Text(stringResource(R.string.settings_change_password_current)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Primary,
                        unfocusedContainerColor = Primary,
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        focusedLabelColor = Gray,
                        unfocusedLabelColor = Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { 
                        newPassword = it
                        showPasswordMismatchError = false
                    },
                    label = { Text(stringResource(R.string.settings_change_password_new)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Primary,
                        unfocusedContainerColor = Primary,
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        focusedLabelColor = Gray,
                        unfocusedLabelColor = Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { 
                        confirmPassword = it
                        showPasswordMismatchError = false
                    },
                    label = { Text(stringResource(R.string.settings_change_password_confirm)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Primary,
                        unfocusedContainerColor = Primary,
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        focusedLabelColor = Gray,
                        unfocusedLabelColor = Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                if (showPasswordMismatchError) {
                    Text(
                        text = stringResource(R.string.settings_change_password_error_mismatch),
                        color = Red,
                        style = DefaultTextStyle,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Button(
                    onClick = {
                        println("New password: '$newPassword'")
                        println("Confirm password: '$confirmPassword'")
                        println("Are equal: ${newPassword == confirmPassword}")
                        
                        if (newPassword.trim() == confirmPassword.trim()) {
                            onConfirm(currentPassword, newPassword, confirmPassword)
                        } else {
                            showPasswordMismatchError = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Complementary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.dialog_confirm),
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(R.string.dialog_cancel),
                        color = Complementary
                    )
                }
            }
        }
    }
} 