package com.example.us.ui.screen.diary

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.us.ui.screen.calendar.COLOR_PINK
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DiaryEventDialog(
    event: DiaryEvent,
    onDismiss: () -> Unit,
    onEdit: (DiaryEvent) -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(Modifier.padding(18.dp)) {
                // Фото
                event.imageUri?.let { uriStr ->
                    AsyncImage(
                        model = uriStr,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(Modifier.height(14.dp))
                }

                Text(event.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2B2025))
                Spacer(Modifier.height(8.dp))

                val dateStr = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(event.date))
                Text(dateStr, fontSize = 14.sp, color = Color(0xFF9A7A88))
                Spacer(Modifier.height(12.dp))

                Text(event.description, fontSize = 16.sp, color = Color(0xFF4A3A42), lineHeight = 20.sp)
                Spacer(Modifier.height(20.dp))

                Box(modifier = Modifier.background(eventColor(event.type).copy(alpha = 0.12f), shape = RoundedCornerShape(10.dp)).padding(horizontal = 14.dp, vertical = 8.dp)) {
                    Text(text = eventTypeRussian(event.type), color = eventColor(event.type), fontWeight = FontWeight.Medium)
                }

                Spacer(Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { onEdit(event) }) {
                        Text("Редактировать", color = COLOR_PINK)
                    }
                    TextButton(onClick = onDismiss) {
                        Text("Закрыть", color = Color.Gray)
                    }
                }
            }
        }
    }
}



