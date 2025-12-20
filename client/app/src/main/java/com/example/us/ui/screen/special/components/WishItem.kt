package com.example.us.ui.screen.special.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import com.example.us.ui.screen.calendar.COLOR_WHITE
import com.example.us.ui.screen.special.classes.Wish
import com.example.us.ui.theme.COLOR_PINK_BG

// WishItem.kt — универсальная карточка желания
@Composable
fun WishItem(
    wish: Wish,
    isOwn: Boolean = true,
    onDone: (() -> Unit)? = null,
    onFavorite: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    showLinkIcon: Boolean = false
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = COLOR_WHITE)
                .padding(12.dp)
        ) {
            // Категория
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(COLOR_PINK_BG),
                contentAlignment = Alignment.Center
            ) {
                Text(wish.categoryIcon ?: "💖", fontSize = 24.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = wish.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF6A1B4D),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (showLinkIcon && !wish.link.isNullOrBlank()) {
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Link, // Можно заменить на иконку ссылки
                            contentDescription = "link",
                            tint = Color(0xFF6A1B4D),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (wish.isFavorite) {
                        Text("Важное", fontSize = 12.sp, color = Color(0xFF6A1B4D))
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onFavorite?.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "favorite",
                        tint = if (wish.isFavorite) Color(0xFFFFC107) else Color(0xFFBDBDBD)
                    )
                }
                if (isOwn) {
                    ChecklistBox(isDone = wish.isDone, onToggle = { onDone?.invoke() })
                }
            }
        }
    }
}



