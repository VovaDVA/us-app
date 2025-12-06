package com.example.us.ui.screen.special.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ChecklistBox(isDone: Boolean, onToggle: () -> Unit) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isDone) Color(0xFF00FF69).copy(alpha = 0.12f) else Color.Transparent)
            .border(width = 1.dp, color = if (isDone) Color.Transparent else Color(0xFFBDBDBD), shape = RoundedCornerShape(10.dp))
            .clickable { onToggle() },
        contentAlignment = Alignment.Center
    ) {
        if (isDone) {
            Text("✓", color = Color(0xFF00FF69), fontWeight = FontWeight.Bold)
        }
    }
}
