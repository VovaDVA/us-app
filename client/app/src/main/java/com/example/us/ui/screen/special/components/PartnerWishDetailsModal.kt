package com.example.us.ui.screen.special.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.special.classes.Wish
import com.example.us.ui.theme.COLOR_PINK_BG

@Composable
fun PartnerWishDialog(
    wish: Wish, onDismiss: () -> Unit
) {
    androidx.compose.material3.AlertDialog(onDismissRequest = onDismiss, title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Иконка категории в розовом квадратике
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(COLOR_PINK_BG, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(wish.categoryIcon, fontSize = 18.sp)
            }

            Spacer(Modifier.width(8.dp))

            Text(
                wish.title, fontSize = 20.sp, fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.width(8.dp))
        }
    }, text = {
        Column {
            if (wish.description.isNotBlank()) {
                Text(
                    text = wish.description, fontSize = 16.sp, color = Color(0xFF2B2025)
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }, confirmButton = {
        androidx.compose.material3.TextButton(onClick = onDismiss) {
            Text("Закрыть")
        }
    })
}
