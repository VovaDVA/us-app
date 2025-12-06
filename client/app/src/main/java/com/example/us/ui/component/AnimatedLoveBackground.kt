package com.example.us.ui.component

import android.graphics.BlurMaskFilter
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedLoveBackground(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()

    // Базовый фон
    val baseColor1 by transition.animateColor(
        initialValue = Color(0xFFFFDAEB),
        targetValue = Color(0xFFFFBBDC),
        animationSpec = infiniteRepeatable(
            tween(5000, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )
    val baseColor2 by transition.animateColor(
        initialValue = Color(0xFFFFBED7),
        targetValue = Color(0xFFFFE5C7),
        animationSpec = infiniteRepeatable(
            tween(5000, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    // Два "светящихся облака"
    val blob1Offset by transition.animateFloat(
        initialValue = -300f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(7000, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    val blob2Offset by transition.animateFloat(
        initialValue = 700f,
        targetValue = -500f,
        animationSpec = infiniteRepeatable(
            tween(7000, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .background(Brush.verticalGradient(listOf(baseColor1, baseColor2)))
    ) {
        Canvas(modifier = Modifier.fillMaxSize().blur(radius = 75.dp)) {

            // Blob 1 — теплое розовое пятно
            drawCircle(
                color = Color(0xFFFFA3C8).copy(alpha = 0.35f),
                radius = size.minDimension * 0.65f,
                center = Offset(size.width * 0.25f + blob1Offset, size.height * 0.3f)
            )

            // Blob 2 — нежное пурпурное пятно
            drawCircle(
                color = Color(0xFFFF8AEC).copy(alpha = 0.30f),
                radius = size.minDimension * 0.75f,
                center = Offset(size.width * 0.75f + blob2Offset, size.height * 0.7f)
            )
        }
    }
}
