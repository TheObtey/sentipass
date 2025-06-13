package fr.theobtey.sentipass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.theme.Gray
import fr.theobtey.sentipass.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection(
    onFilterClick: () -> Unit
) {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = stringResource(R.string.entry_search_bar_hint),
                    color = Gray
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    tint = Gray,
                    modifier = Modifier
                        .size(32.dp)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2B2F36),
                unfocusedContainerColor = Color(0xFF2B2F36),
                disabledContainerColor = Color(0xFF2B2F36),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = White,
                unfocusedTextColor = White,
                disabledTextColor = White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        )

        Spacer(modifier = Modifier.width(18.dp))

        IconButton(
            onClick = onFilterClick,
            modifier = Modifier
                .size(32.dp)
                .background(Color(0xFF2B2F36), RoundedCornerShape(16.dp))
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = "Filter Icon",
                tint = Gray
            )
        }
    }
}