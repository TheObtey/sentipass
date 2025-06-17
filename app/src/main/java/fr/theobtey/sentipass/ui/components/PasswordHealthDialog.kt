package fr.theobtey.sentipass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.data.model.PasswordResponse
import fr.theobtey.sentipass.ui.theme.*
import fr.theobtey.sentipass.utils.analyzePasswords
import fr.theobtey.sentipass.utils.generatePassword
import fr.theobtey.sentipass.viewmodel.PasswordViewModel

// Data class for password analysis results
data class PasswordHealthResult(
    val password: PasswordResponse,
    val strength: String,
    val colorRes: Int,
    val isReused: Boolean
)

// Main dialog component
@Composable
fun PasswordHealthDialog(
    onDismiss: () -> Unit,
    viewModel: PasswordViewModel
) {
    val passwords by viewModel.passwords.collectAsState()
    var analysisResults by remember { mutableStateOf<List<PasswordHealthResult>>(emptyList()) }

    DialogContent(
        onDismiss = onDismiss,
        analysisResults = analysisResults,
        onAnalyze = { analysisResults = analyzePasswords(passwords) }
    )
}

// Dialog content component
@Composable
private fun DialogContent(
    onDismiss: () -> Unit,
    analysisResults: List<PasswordHealthResult>,
    onAnalyze: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Smoke)
            .clickable(enabled = true, onClick = {})
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .background(Primary, RoundedCornerShape(16.dp))
                .padding(24.dp)
                .widthIn(min = 320.dp, max = 400.dp)
                .heightIn(min = 400.dp, max = 600.dp)
        ) {
            DialogHeader(onDismiss)
            Spacer(modifier = Modifier.height(24.dp))
            AnalyzeButton(onAnalyze)
            ResultsList(analysisResults)
        }
    }
}

// Dialog header component
@Composable
private fun DialogHeader(onDismiss: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onDismiss) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = White
            )
        }
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = "Password Health",
            style = PasswordDetailsTitleStyle
        )
    }
}

// Analyze button component
@Composable
private fun AnalyzeButton(onAnalyze: () -> Unit) {
    Button(
        onClick = onAnalyze,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Complementary)
    ) {
        Text(
            text = stringResource(R.string.button_analyze),
            style = TitleTextStyle,
            color = White
        )
    }
}

// Results list component
@Composable
private fun ResultsList(results: List<PasswordHealthResult>) {
    if (results.isNotEmpty()) {
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(results) { result ->
                PasswordHealthItem(result)
            }
        }
    } else {
        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Individual password health item component
@Composable
private fun PasswordHealthItem(result: PasswordHealthResult) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ServiceName(result.password.service)
        StrengthIndicator(result.strength, result.colorRes)
        FixButton()
    }
}

// Service name component
@Composable
private fun ServiceName(service: String) {
    Text(
        text = service,
        style = PasswordDetailsSubtitleStyle,
        color = White,
        maxLines = 1,
        modifier = Modifier.fillMaxWidth(0.5f)
    )
}

// Strength indicator component
@Composable
private fun StrengthIndicator(strength: String, colorRes: Int) {
    Text(
        text = strength,
        style = PasswordDetailsSubtitleStyle,
        color = androidx.compose.ui.graphics.Color(
            androidx.core.content.ContextCompat.getColor(
                LocalContext.current,
                colorRes
            )
        ),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

// Fix button component
@Composable
private fun FixButton() {
    Button(
        onClick = { 
            val strongPassword = generatePassword(
                length = 24,
                useUppercase = true,
                useDigits = true,
                useSymbols = true
            )
            println("Generated strong password: $strongPassword")
        },
        colors = ButtonDefaults.buttonColors(containerColor = Complementary),
        modifier = Modifier.size(width = 80.dp, height = 30.dp)
    ) {
        Text(
            text = "FIX",
            style = PasswordDetailsSubtitleStyle,
            color = White
        )
    }
} 