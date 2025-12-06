package com.example.us.ui.screen.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.calendar.COLOR_PINK

@Composable
fun AddEventSheet(
    initialText: String? = null,
    initialIcon: String = "🎀",
    isEditing: Boolean = false,
    onConfirm: (String, String) -> Unit,  // текст + иконка
    onClose: () -> Unit
) {
    var input by remember { mutableStateOf(initialText ?: "") }
    var selectedIcon by remember { mutableStateOf(initialIcon) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Новое событие",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = COLOR_PINK
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = null, tint = COLOR_PINK)
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Событие") },
            placeholder = { Text("Что делаем?") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )


        Spacer(Modifier.height(16.dp))

        Text("Выбери иконку", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(Modifier.height(16.dp))

        val icons = listOf("🎀", "🎉", "❤️", "✨", "🍫", "🌹", "💌")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(icons) { ic ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (selectedIcon == ic) Color(0xFFFFC1E0) else Color(0xFFFFC1E0).copy(alpha = 0.3f))
                        .clickable { selectedIcon = ic },
                    contentAlignment = Alignment.Center
                ) {
                    Text(ic, fontSize = 24.sp, color = if (selectedIcon == ic) Color.White else Color.White.copy(alpha = 0.7f))
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onConfirm(input.ifBlank { "Новое событие" }, selectedIcon) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(COLOR_PINK)
        ) {
            Text(if (isEditing) "Сохранить" else "Добавить", color = Color.White)
        }
    }
}
