package fr.theobtey.sentipass.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fr.theobtey.sentipass.R

// Déclaration des fonts custom
val PlayFontFamily = FontFamily(
    Font(R.font.play_regular, FontWeight.Normal),
    Font(R.font.play_bold, FontWeight.Bold)
)

// Correspondances des styles XML en Compose
val AppNameTextStyle = TextStyle(
    fontFamily = PlayFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 38.sp,
    color = White
)

val HeaderTextStyle = TextStyle(
    fontFamily = PlayFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    color = White
)

val DefaultTextStyle = TextStyle(
    fontFamily = PlayFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    color = Gray
)

val PasswordDetailsTitleStyle = DefaultTextStyle.copy(
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp
)

val PasswordDetailsSubtitleStyle = PasswordDetailsTitleStyle.copy(
    fontWeight = FontWeight.Normal,
    color = White
)

val CategoryNameTextStyle = DefaultTextStyle.copy(
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp
)

val SubtitleTextStyle = DefaultTextStyle.copy(
    fontWeight = FontWeight.Bold,
    color = White
)

val TitleTextStyle = SubtitleTextStyle.copy(
    fontSize = 24.sp
)

// Pour MaterialTheme.typography si tu veux les brancher par défaut
val Typography = Typography(
    bodyLarge = DefaultTextStyle,
    headlineMedium = TitleTextStyle
)
