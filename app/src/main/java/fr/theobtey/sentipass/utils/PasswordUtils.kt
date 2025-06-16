package fr.theobtey.sentipass.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.data.model.PasswordResponse
import fr.theobtey.sentipass.ui.components.PasswordHealthResult
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
        in 0..2 -> "Weak" to R.color.flashy_red
        in 3..4 -> "Medium" to R.color.flashy_yellow
        else -> "Strong" to R.color.flashy_green
    }
}

/* TODO: Find a way to merge getPasswordStrength and getPasswordStrengthNonComposable */
fun getPasswordStrengthNonComposable(password: String): Pair<String, Int> {
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
        in 0..2 -> "Weak" to R.color.flashy_red
        in 3..4 -> "Medium" to R.color.flashy_yellow
        else -> "Strong" to R.color.flashy_green
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

fun analyzePasswords(passwords: List<PasswordResponse>): List<PasswordHealthResult> {
    val results = mutableListOf<PasswordHealthResult>()
    val passwordCounts = passwords.groupingBy { it.password }.eachCount()

    passwords.forEach { password ->
        val isReused = (passwordCounts[password.password] ?: 0) > 1
        val (strength, _) = getPasswordStrengthNonComposable(password.password)
        
        if (strength != "Strong") {
            results.add(
                PasswordHealthResult(
                    password = password,
                    strength = strength,
                    colorRes = when (strength) {
                        "Weak" -> R.color.flashy_red
                        "Medium" -> R.color.flashy_yellow
                        else -> R.color.flashy_green
                    },
                    isReused = isReused
                )
            )
        }
    }

    return results
}