package fr.theobtey.sentipass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf("home") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Secondary),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarItem(
            icon = R.drawable.ic_home,
            label = "Home",
            selected = selected == "home",
            onClick = { selected = "home" }
        )
        BottomBarItem(
            icon = R.drawable.ic_password,
            label = "Passwords",
            selected = selected == "passwords",
            onClick = { selected = "passwords" }
        )
        BottomBarItem(
            icon = R.drawable.ic_tools,
            label = "Tools",
            selected = selected == "tools",
            onClick = { selected = "tools" }
        )
        BottomBarItem(
            icon = R.drawable.ic_settings,
            label = "Settings",
            selected = selected == "settings",
            onClick = { selected = "settings" }
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