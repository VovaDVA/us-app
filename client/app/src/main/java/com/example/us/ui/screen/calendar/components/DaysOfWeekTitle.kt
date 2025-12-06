package com.example.us.ui.screen.calendar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.calendar.COLOR_PINK
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DaysOfWeekTitle(days: List<DayOfWeek>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        days.forEach {
            Text(
                text = it.getDisplayName(TextStyle.SHORT, Locale("ru")),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = COLOR_PINK
            )
        }
    }
}