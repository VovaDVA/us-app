package com.example.us.ui.screen.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun GameCard(title: String, description: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.85f))
            .clickable { onClick() }
            .padding(18.dp)
    ) {
        Column {
            Text(title, fontSize = 22.sp, color = Color(0xFFFF6BAB), fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(description, fontSize = 16.sp, color = Color(0xFFFF6BAB))
        }
    }
}

