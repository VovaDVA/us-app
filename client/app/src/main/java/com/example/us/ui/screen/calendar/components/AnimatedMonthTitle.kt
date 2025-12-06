package com.example.us.ui.screen.calendar.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.calendar.COLOR_PINK
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedMonthTitle(yearMonth: YearMonth) {
    Box(
        modifier = Modifier
            .padding(top = WindowInsets.statusBars.asPaddingValues()
                .calculateTopPadding() + 10.dp)
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .background(
                color = COLOR_PINK,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(12.dp)
    ) {
        AnimatedContent(
            targetState = yearMonth,
            transitionSpec = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) with
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up)
            }
        ) { ym ->
            Text(
                text = ym.month
                    .getDisplayName(TextStyle.FULL_STANDALONE, Locale("ru"))
                    .replaceFirstChar { it.uppercase() } + " ${ym.year}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}