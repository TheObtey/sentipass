package fr.theobtey.sentipass.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fr.theobtey.sentipass.R
import kotlin.random.Random

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

fun generatePassword(
    length: Int,
    useUppercase: Boolean,
    useDigits: Boolean,
    useSymbols: Boolean
): String {
    val lowercase = "abcdefghijklmnopqrstuvwxyz"
    val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val digits = "0123456789"
    val symbols = "!@#\$%&*?+-_"

    var charPool = lowercase
    if (useUppercase) charPool += uppercase
    if (useDigits) charPool += digits
    if (useSymbols) charPool += symbols

    if (charPool.isEmpty()) return ""

    return (1..length)
        .map { charPool[Random.nextInt(charPool.length)] }
        .joinToString("")
}