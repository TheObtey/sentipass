package fr.theobtey.sentipass.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.components.*
import fr.theobtey.sentipass.ui.theme.*
import androidx.navigation.NavController
import fr.theobtey.sentipass.data.network.RetrofitClient
import fr.theobtey.sentipass.repository.PasswordRepository
import fr.theobtey.sentipass.viewmodel.PasswordViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.theobtey.sentipass.data.model.PasswordResponse
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.media.MediaScannerConnection
import android.os.Environment

@Composable
fun ToolsScreen(
    token: String,
    navController: NavController
) {
    val context = LocalContext.current
    val toolOptions = context.resources.getStringArray(R.array.tool_page_options)
    var showPasswordGeneratorDialog by remember { mutableStateOf(false) }
    var showPasswordHealthDialog by remember { mutableStateOf(false) }
    var showExportSuccessDialog by remember { mutableStateOf(false) }
    var showExportErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    val repository = remember { PasswordRepository(RetrofitClient.api) }
    val passwordViewModel = remember { PasswordViewModel(repository) }

    // Fetch passwords when the screen is loaded
    LaunchedEffect(Unit) {
        passwordViewModel.fetchPasswords(token)
    }

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

            // Tools Grid
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ToolCard(
                    title = toolOptions[0],
                    icon = R.drawable.password_generator,
                    onClick = { showPasswordGeneratorDialog = true }
                )
                
                ToolCard(
                    title = toolOptions[1],
                    icon = R.drawable.check_password,
                    onClick = { showPasswordHealthDialog = true }
                )
                
                ToolCard(
                    title = toolOptions[2],
                    icon = R.drawable.export_passwords,
                    onClick = { 
                        try {
                            exportPasswords(context, passwordViewModel.passwords.value)
                            showExportSuccessDialog = true
                        } catch (e: Exception) {
                            errorMessage = e.message ?: "Unknown error occurred"
                            showExportErrorDialog = true
                        }
                    }
                )
            }
        }

        if (showPasswordGeneratorDialog) {
            PasswordGeneratorDialog(onDismiss = { showPasswordGeneratorDialog = false })
        }

        if (showPasswordHealthDialog) {
            PasswordHealthDialog(
                onDismiss = { showPasswordHealthDialog = false },
                viewModel = passwordViewModel
            )
        }

        if (showExportSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showExportSuccessDialog = false },
                title = { Text("Success") },
                text = { 
                    Text(
                        "Passwords exported successfully to:\n" +
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { showExportSuccessDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Complementary)
                    ) {
                        Text("OK")
                    }
                },
                containerColor = Primary
            )
        }

        if (showExportErrorDialog) {
            AlertDialog(
                onDismissRequest = { showExportErrorDialog = false },
                title = { Text("Error") },
                text = { Text(errorMessage) },
                confirmButton = {
                    Button(
                        onClick = { showExportErrorDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Complementary)
                    ) {
                        Text("OK")
                    }
                },
                containerColor = Primary
            )
        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            navController = navController,
            currentRoute = "tools/$token"
        )
    }
}

private fun exportPasswords(context: Context, passwords: List<PasswordResponse>) {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val json = gson.toJson(passwords)
    
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val fileName = "sentipass_export_$timestamp.json"
    
    // Get the public Downloads directory
    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, fileName)
    
    try {
        file.writeText(json)
        // Notify the media scanner to make the file visible in the Downloads app
        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.absolutePath),
            null,
            null
        )
    } catch (e: Exception) {
        throw Exception("Failed to save file: ${e.message}")
    }
}

@Composable
fun ToolCard(
    title: String,
    icon: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = Complementary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = DefaultTextStyle,
                color = White
            )
        }
    }
} 