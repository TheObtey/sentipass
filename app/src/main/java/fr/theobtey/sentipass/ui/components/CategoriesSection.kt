package fr.theobtey.sentipass.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.theobtey.sentipass.R
import fr.theobtey.sentipass.ui.theme.CategoryNameTextStyle
import fr.theobtey.sentipass.ui.theme.DefaultTextStyle
import fr.theobtey.sentipass.ui.theme.Gray
import fr.theobtey.sentipass.ui.theme.TitleTextStyle
import fr.theobtey.sentipass.ui.theme.White

data class Category(
    val label: String,
    val iconRes: Int
)

private val categoryList = listOf(
    Category("Apps", R.drawable.ic_app),
    Category("Email", R.drawable.ic_email),
    Category("Banking", R.drawable.ic_banking),
    Category("Notes", R.drawable.ic_notes),
    Category("Other", R.drawable.ic_other)
)

@Composable
fun CategoriesSection() {
    Column {
        Text(
            text = stringResource(R.string.home_page_category),
            style = DefaultTextStyle,
            color = White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(categoryList) { category ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(64.dp)
                        .clickable { /* TODO : Filtrer par catégories */ }
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Gray.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(category.iconRes),
                            contentDescription = category.label,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = category.label,
                        style = CategoryNameTextStyle
                    )
                }
            }
        }
    }
}