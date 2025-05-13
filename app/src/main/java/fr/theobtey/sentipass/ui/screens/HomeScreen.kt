package fr.theobtey.sentipass.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.theobtey.sentipass.ui.components.BottomBar
import fr.theobtey.sentipass.ui.components.CategoriesSection
import fr.theobtey.sentipass.ui.components.HeaderSection
import fr.theobtey.sentipass.ui.components.PasswordListSection
import fr.theobtey.sentipass.ui.components.SearchSection
import fr.theobtey.sentipass.ui.theme.Primary

@Composable
fun HomeScreen() {
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

            Spacer(modifier = Modifier.height(80.dp))
        }

        // AddButton(modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp))

        BottomBar(modifier = Modifier.align(Alignment.BottomCenter))
    }
}