package com.example.us.ui.screen.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.calendar.COLOR_PINK
import com.example.us.ui.screen.calendar.COLOR_WHITE
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition

@Composable
fun DayCell(
    day: CalendarDay,
    isSelected: Boolean,
    hasEvents: Boolean,
    onClick: () -> Unit
) {
    val isFromCurrentMonth = day.position == DayPosition.MonthDate

    val textColor = when {
        !isFromCurrentMonth -> COLOR_PINK.copy(alpha = 0.5f)
        isSelected -> Color.White
        else -> COLOR_PINK
    }

    val bgColor = when {
        !isFromCurrentMonth -> Color.White.copy(alpha = 0.4f)
        isSelected -> COLOR_PINK
        hasEvents -> COLOR_PINK.copy(alpha = 0.2f)
        else -> COLOR_WHITE
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            day.date.dayOfMonth.toString(),
            fontSize = 18.sp,
            color = textColor
        )
    }
}