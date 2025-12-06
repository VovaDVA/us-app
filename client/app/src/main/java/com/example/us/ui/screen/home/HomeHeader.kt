package com.example.us.ui.screen.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.calendar.COLOR_WHITE

@Composable
fun HomeHeader() {
    val pulse by animateFloatAsState(
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(listOf(Color(0xFFFF85C6), Color(0xFFFF5EA2))),
                RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // аватарка с мягким пульсом
            Box(
                modifier = Modifier
                    .size((70 * pulse).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.88f)),
                contentAlignment = Alignment.Center
            ) {
                Text("💖", fontSize = 36.sp)
            }

            Spacer(Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.height(70.dp)
            ) {
                Text(
                    "Уголок счастья",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "Для тебя и для меня",
                    fontSize = 16.sp,
                    color = COLOR_WHITE
                )
            }
        }
    }
}
