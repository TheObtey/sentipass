package fr.theobtey.sentipass.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.data.model.PasswordResponse
import fr.theobtey.sentipass.repository.PasswordRepository
import fr.theobtey.sentipass.ui.components.*
import fr.theobtey.sentipass.ui.theme.*
import fr.theobtey.sentipass.viewmodel.PasswordViewModel
import fr.theobtey.sentipass.data.network.RetrofitClient
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    token: String,
    navController: NavController
) {
    val repository = remember { PasswordRepository(RetrofitClient.api) }
    val passwordViewModel = remember { PasswordViewModel(repository) }
    var showAddPasswordDialog by remember { mutableStateOf(false) }
    var selectedPassword by remember { mutableStateOf<PasswordResponse?>(null) }
    var showCategories by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val passwords by passwordViewModel.passwords.collectAsState()

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

            SearchSection(
                onFilterClick = { showCategories = !showCategories },
                onSearch = { searchQuery = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // if (showCategories) {
            //     CategoriesSection()
            //     Spacer(modifier = Modifier.height(24.dp))
            // }

            PasswordListSection(
                passwords = passwords.filter { password ->
                    password.service.contains(searchQuery, ignoreCase = true) ||
                    (password.email?.contains(searchQuery, ignoreCase = true) ?: false) ||
                    (password.username?.contains(searchQuery, ignoreCase = true) ?: false)
                },
                onPasswordClick = { selectedPassword = it },
                isCategoryVisible = showCategories,
                viewModel = passwordViewModel,
                token = token
            )
        }

        FloatingActionButton(
            onClick = { showAddPasswordDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 18.dp, bottom = 128.dp),
            containerColor = Color.Transparent
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = "Add Password",
                tint = Complementary
            )
        }

        if (showAddPasswordDialog) {
            AddPasswordDialog(
                onDismiss = { showAddPasswordDialog = false },
                viewModel = passwordViewModel,
                token = token
            )
        }

        if (selectedPassword != null) {
            PasswordDetailsDialog(
                password = selectedPassword!!,
                onClose = { selectedPassword = null },
                onCopy = { /* TODO: Implement copy functionality */ },
                onEdit = { /* TODO: Implement edit functionality */ },
                viewModel = passwordViewModel,
                token = token
            )
        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            navController = navController,
            currentRoute = "home/$token"
        )
    }
}