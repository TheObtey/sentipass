package fr.theobtey.sentipass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.theobtey.sentipass.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.theobtey.sentipass.ui.theme.Complementary
import fr.theobtey.sentipass.ui.theme.DefaultTextStyle
import fr.theobtey.sentipass.ui.theme.Gray
import fr.theobtey.sentipass.ui.theme.Secondary
import fr.theobtey.sentipass.ui.theme.White
import androidx.navigation.NavController

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentRoute: String
) {
    Row(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .fillMaxWidth()
            .height(80.dp)
            .background(
                color = Secondary,
                shape = RoundedCornerShape(24.dp)
            ),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarItem(
            icon = R.drawable.ic_home,
            label = "Home",
            selected = currentRoute.startsWith("home"),
            onClick = { 
                if (!currentRoute.startsWith("home")) {
                    navController.navigate("home/${currentRoute.split("/").lastOrNull() ?: ""}")
                }
            }
        )
        BottomBarItem(
            icon = R.drawable.ic_password,
            label = "Passwords",
            selected = currentRoute.startsWith("passwords"),
            onClick = { /* TODO: Implement passwords navigation */ }
        )
        BottomBarItem(
            icon = R.drawable.ic_tools,
            label = "Tools",
            selected = currentRoute.startsWith("tools"),
            onClick = { 
                if (!currentRoute.startsWith("tools")) {
                    navController.navigate("tools/${currentRoute.split("/").lastOrNull() ?: ""}")
                }
            }
        )
        BottomBarItem(
            icon = R.drawable.ic_settings,
            label = "Settings",
            selected = currentRoute.startsWith("settings"),
            onClick = { /* TODO: Implement settings navigation */ }
        )
    }
}

@Composable
fun BottomBarItem(
    icon: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = if (selected) Complementary else Gray,
            modifier = Modifier.size(38.dp)
        )
    }
}