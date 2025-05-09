package fr.theobtey.sentipass.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.theme.HeaderTextStyle

@Composable
fun HeaderSection() {
    Spacer(modifier = Modifier.height(42.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment =  Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            androidx.compose.material3.Text(
                text = stringResource(R.string.app_name),
                style = HeaderTextStyle
            )
        }

        IconButton(onClick = { /* TODO : Profil utilisateur */ }) {
            Icon(
                painter = painterResource(R.drawable.ic_user),
                contentDescription = "Profile",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}