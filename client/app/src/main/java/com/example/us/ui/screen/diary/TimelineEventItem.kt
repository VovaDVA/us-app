package com.example.us.ui.screen.diary

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TimelineEventItem(
    event: DiaryEvent,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { onClick() }
    ) {
        // ⬤ точка
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(Color.White, CircleShape)
        )

        Spacer(Modifier.width(16.dp))

        // карточка
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.15f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(Modifier.padding(16.dp)) {

                val daysAgo = ((System.currentTimeMillis() - event.date) / 86400000L).toInt()
                Text(if (daysAgo == 0) "Сегодня" else "$daysAgo дней назад",
                    style = MaterialTheme.typography.labelMedium)

                Spacer(Modifier.height(4.dp))
                Text(event.title, style = MaterialTheme.typography.titleMedium)

                event.imageUri?.let {
                    Spacer(Modifier.height(8.dp))
                    AsyncImage(
                        model = event.imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
