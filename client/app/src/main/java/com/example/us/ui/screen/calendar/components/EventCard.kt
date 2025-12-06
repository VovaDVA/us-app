package com.example.us.ui.screen.calendar.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.calendar.COLOR_PINK
import com.example.us.ui.screen.calendar.COLOR_WHITE
import java.time.LocalDate

@Composable
fun EventCard(
    text: String,
    icon: String = "🎀",
    date: LocalDate,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(COLOR_WHITE)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFFC1E0).copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 24.sp)
        }
        Spacer(Modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.weight(1f).height(50.dp)
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF6A1B4D),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "${date.dayOfMonth}.${date.monthValue}.${date.year}",
                fontSize = 14.sp,
                color = Color(0xFF6A1B4D).copy(alpha = 0.7f)
            )
        }

        Row {
            IconButton(onClick = onEdit) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = null,
                    tint = COLOR_PINK.copy(alpha = 0.7f)
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    tint = COLOR_PINK.copy(alpha = 0.7f)
                )
            }
        }
    }
}


