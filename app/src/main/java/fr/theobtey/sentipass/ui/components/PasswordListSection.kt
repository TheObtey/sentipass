package fr.theobtey.sentipass.ui.components

import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.data.model.PasswordResponse
import fr.theobtey.sentipass.ui.theme.DefaultTextStyle
import fr.theobtey.sentipass.ui.theme.Primary
import fr.theobtey.sentipass.ui.theme.White
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage

data class PasswordItem(
    val name: String,
    val username: String
)

@Composable
fun PasswordListSection(passwords: List<PasswordResponse>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.home_page_most_recent),
            style = DefaultTextStyle,
            color = White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Box(
            modifier = Modifier
                .height(420.dp)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp)
            ) {
                items(passwords) { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .clickable { /* TODO: Show details card */ }
                    ) {
                        AsyncImage(
                            model = "https://www.google.com/s2/favicons?sz=64&domain_url=${item.url}",
                            contentDescription = item.service,
                            modifier = Modifier.size(32.dp),
                            error = painterResource(R.drawable.ic_password),
                            fallback = painterResource(R.drawable.ic_password)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = item.service,
                                style = DefaultTextStyle
                            )
                            Text(
                                text = item.email ?: item.username?: "",
                                style = DefaultTextStyle
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Primary)
                        )
                    )
            )
        }
    }
}