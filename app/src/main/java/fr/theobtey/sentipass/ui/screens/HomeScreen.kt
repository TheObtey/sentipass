package fr.theobtey.sentipass.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.components.AddPasswordDialog
import fr.theobtey.sentipass.ui.components.BottomBar
import fr.theobtey.sentipass.ui.components.CategoriesSection
import fr.theobtey.sentipass.ui.components.HeaderSection
import fr.theobtey.sentipass.ui.components.PasswordListSection
import fr.theobtey.sentipass.ui.components.SearchSection
import fr.theobtey.sentipass.ui.theme.Complementary
import fr.theobtey.sentipass.ui.theme.Primary
import fr.theobtey.sentipass.ui.theme.White

@Composable
fun HomeScreen() {
    var showAddPasswordDialog by remember { mutableStateOf(false) }
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

            SearchSection()

            Spacer(modifier = Modifier.height(32.dp))

            CategoriesSection()

            Spacer(modifier = Modifier.height(32.dp))

            PasswordListSection()
        }

        FloatingActionButton(
            onClick = { showAddPasswordDialog = true },
            shape = CircleShape,
            containerColor = Primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 32.dp, bottom = 124.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add password",
                tint = Complementary
            )
        }

        BottomBar(modifier = Modifier.align(Alignment.BottomCenter))

        if (showAddPasswordDialog) {
            AddPasswordDialog(onDismiss = { showAddPasswordDialog = false })
        }
    }
}