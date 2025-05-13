package fr.theobtey.sentipass.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fr.theobtey.sentipass.R

@Composable
fun getPasswordStrength(password: String): Pair<String, Int> {
    val length = password.length
    val hasUpper = password.any { it.isUpperCase() }
    val hasLower = password.any { it.isLowerCase() }
    val digitCount = password.count { it.isDigit() }
    val context = LocalContext.current
    val paswordStrength = context.resources.getStringArray(R.array.password_strength_levels)

    return when {
        length >= 14 && hasUpper && hasLower && digitCount >= 4 -> paswordStrength[2] to R.color.flashy_green
        length in 8..13 && hasUpper && hasLower && digitCount >= 1 -> paswordStrength[1] to R.color.flashy_yellow
        else -> paswordStrength[0] to R.color.flashy_red
    }
}
