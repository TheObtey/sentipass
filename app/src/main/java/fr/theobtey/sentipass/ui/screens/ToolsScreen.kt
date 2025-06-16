package fr.theobtey.sentipass.ui.screens

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

@Composable
fun ToolsScreen(
    token: String,
    navController: NavController
) {
    val context = LocalContext.current
    val toolOptions = context.resources.getStringArray(R.array.tool_page_options)
    var showPasswordGeneratorDialog by remember { mutableStateOf(false) }
    var showPasswordHealthDialog by remember { mutableStateOf(false) }
    
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
                    onClick = { /* TODO: Implement Export Passwords */ }
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

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            navController = navController,
            currentRoute = "tools/$token"
        )
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