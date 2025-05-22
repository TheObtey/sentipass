package fr.theobtey.sentipass.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fr.theobtey.sentipass.R

@Composable
fun getPasswordStrength(password: String): Pair<String, Int> {
    val length = password.length
    val hasUpper = password.any { it.isUpperCase() }
    val hasLower = password.any { it.isLowerCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasSpecial = password.any { !it.isLetterOrDigit() }

    val score = listOf(hasUpper, hasLower, hasDigit, hasSpecial).count { it } + when {
        length >= 14 -> 2
        length >= 10 -> 1
        else -> 0
    }

    return when (score) {
        in 0..2 -> "Weak password" to R.color.flashy_red
        in 3..4 -> "Medium password" to R.color.flashy_yellow
        else -> "Strong password" to R.color.flashy_green
    }
}