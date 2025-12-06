package com.example.us.ui.screen.home.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.home.InfoCard
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun TimeTogetherCard() {
    val start = LocalDate.of(2024, 7, 14).atStartOfDay()

    var now by remember { mutableStateOf(LocalDateTime.now()) }

    // обновление каждую секунду
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            now = LocalDateTime.now()
        }
    }

    val duration = Duration.between(start, now)

    val days = duration.toDays()
    val hours = duration.toHours() % 24
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    InfoCard(
        title = "Мы вместе",
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            TimeValueAnimated("$days", "дней")
            TimeValueAnimated(String.format("%02d", hours), "часов")
            TimeValueAnimated(String.format("%02d", minutes), "минут")
            TimeValueAnimated(String.format("%02d", seconds), "секунд")
        }
    }
}

@Composable
fun TimeValueAnimated(value: String, label: String) {
    // Анимация появления/смены цифры
    var oldValue by remember { mutableStateOf(value) }
    var animateChange by remember { mutableStateOf(false) }

    // триггерим анимацию при смене числа
    if (value != oldValue) {
        animateChange = true
        oldValue = value
    }

    // движение вверх-вниз
    val offset by animateDpAsState(
        targetValue = if (animateChange) (-6).dp else 0.dp,
        animationSpec = tween(300, easing = LinearEasing),
        finishedListener = { animateChange = false }
    )

    // изменение прозрачности
    val alpha by animateFloatAsState(
        targetValue = if (animateChange) 0.6f else 1f,
        animationSpec = tween(300, easing = LinearEasing)
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = value,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF6BAB),
            modifier = Modifier
                .offset(y = offset)
                .alpha(alpha)
        )

        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFFFF6BAB).copy(alpha = 0.8f)
        )
    }
}
