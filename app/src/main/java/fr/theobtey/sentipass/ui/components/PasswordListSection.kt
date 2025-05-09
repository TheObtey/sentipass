package fr.theobtey.sentipass.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.sp
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.theme.Gray
import fr.theobtey.sentipass.ui.theme.White
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import fr.theobtey.sentipass.ui.theme.DefaultTextStyle
import fr.theobtey.sentipass.ui.theme.Primary
import fr.theobtey.sentipass.ui.theme.SubtitleTextStyle
import fr.theobtey.sentipass.ui.theme.TitleTextStyle

data class PasswordItem(
    val name: String,
    val username: String,
    val iconRes: Int
)

private val passwordList = listOf(
    PasswordItem("Gmail", "example@gmail.com", R.drawable.ic_email),
    PasswordItem("Google", "example@gmail.com", R.drawable.ic_google),
    PasswordItem("Google", "example@gmail.com", R.drawable.ic_google),
    PasswordItem("Google", "example@gmail.com", R.drawable.ic_google),
    PasswordItem("Google", "example@gmail.com", R.drawable.ic_google),
    PasswordItem("Google", "example@gmail.com", R.drawable.ic_google),
    PasswordItem("Google", "example@gmail.com", R.drawable.ic_google),
    PasswordItem("Google", "example@gmail.com", R.drawable.ic_google),
    PasswordItem("Google", "example@gmail.com", R.drawable.ic_google),
    PasswordItem("Google", "example@gmail.com", R.drawable.ic_google),
    PasswordItem("Google", "example@gmail.com", R.drawable.ic_google),
    PasswordItem("Discord", "example@gmail.com", R.drawable.ic_discord),
    PasswordItem("ChatGPT", "example@gmail.com", R.drawable.ic_openai),
    PasswordItem("Spotify", "example@gmail.com", R.drawable.ic_spotify),
    PasswordItem("Figma", "example@gmail.com", R.drawable.ic_figma)
)

@Composable
fun PasswordListSection() {
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
                .height(400.dp)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp)
            ) {
                items(passwordList) { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(38.dp)
                            .fillMaxWidth()
                            .clickable { /* TODO : Ouvrir les détails */ }
                    ) {
                        Image(
                            painter = painterResource(item.iconRes),
                            contentDescription = item.name,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = item.name,
                                style = DefaultTextStyle
                            )
                            Text(
                                text = item.username,
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