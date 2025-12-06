package com.example.us.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.calendar.rememberEventsStore
import com.example.us.ui.screen.home.InfoCard
import java.time.LocalDate

@Composable
fun NextEventCard() {
    val eventsStore = rememberEventsStore()

    // ★ Используем EventItem вместо String
    val nextEvent = remember(eventsStore.events) {
        eventsStore.events
            .filter { it.key >= LocalDate.now() && it.value.isNotEmpty() }
            .toSortedMap()
            .map { entry ->
                val event = entry.value.firstOrNull()
                event?.let { entry.key to it } // Pair<LocalDate, EventItem>
            }
            .firstOrNull()
    }

    InfoCard(title = "Ближайшее событие", modifier = Modifier.fillMaxWidth()) {
        if (nextEvent != null) {
            val (date, event) = nextEvent

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFFC1E0).copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    // ★ Показываем иконку события
                    Text(event.icon, fontSize = 24.sp)
                }

                Spacer(Modifier.width(12.dp))

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.height(50.dp)
                ) {
                    // ★ Показываем текст события
                    Text(
                        event.text,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF6A1B4D)
                    )
                    Text(
                        "С ${date.dayOfMonth}.${date.monthValue}.${date.year}",
                        fontSize = 14.sp,
                        color = Color(0xFF6A1B4D).copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            Text(
                "Событий нет",
                fontSize = 16.sp,
                color = Color(0xFF6A1B4D).copy(alpha = 0.7f)
            )
        }
    }
}
