package com.example.us.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun BottomMenu(
    tabs: List<TabItem>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 1f),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp) // верхние скругления
            )
            .navigationBarsPadding() // учитываем нижнюю панель телефона
            .padding(vertical = 12.dp, horizontal = 12.dp), // чуть меньше вертикального отступа
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        tabs.forEachIndexed { index, tab ->
            remember { MutableInteractionSource() }
            val isSelected = index == selectedIndex
            if (isSelected) Color(0xFFFFC0CB) else Color.Gray.copy(alpha = 0.5f) // прозрачные неактивные

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                // временный эффект нажатия
                                onTabSelected(index)
                                tryAwaitRelease()
                            }
                        )
                    }
                    .padding(vertical = 4.dp)
            ) {
                val iconAlpha = if (index == selectedIndex) 1f else 0.5f
                CompositionLocalProvider(LocalContentColor provides Color(0xFFFFC0CB)) {
                    Box(modifier = Modifier.alpha(iconAlpha)) {
                        tab.icon()
                    }
                }
            }
        }
    }
}