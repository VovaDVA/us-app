package com.example.us.ui.screen.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.home.InfoCard

@Composable
fun QuoteBlock(quote: Quote) {
    InfoCard(title = "Цитата дня") {

        Text(
            text = "«${quote.text}»",
            fontSize = 18.sp,
            color = Color(0xFF5A4A55),
            lineHeight = 24.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = quote.author,
            fontSize = 14.sp,
            color = Color(0xFF5A4A55).copy(alpha = 0.65f),
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


data class Quote(
    val text: String,
    val author: String
)
