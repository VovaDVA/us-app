package com.example.us.ui.screen.special.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmojiGridSelector(
    selectedIcon: String,
    onSelect: (String) -> Unit
) {
    val emojis = listOf(
        // ❤️ Любовь и эмоции
        "💖","💗","💓","💞","💕","💘","💝","💟",
        "❤️","🧡","💛","💚","💙","💜","🖤","🤍","🤎",
        "✨","🌟","⭐","⚡","🔥","🌈",

        // 😊 Эмоции / лица
        "😀","😃","😄","😁","😆","😊","😉","😍","😘","😗","😚","😙",
        "🥰","🤩","🤗","🙂","😌","😇","😭","😢","😅","😤","😎",

        // 🎉 Праздники
        "🎉","🎊","🎁","🎀","🎈","🥳","🎂","🍰",

        // 🌸 Природа
        "🌸","🌺","🌻","🌼","🌷","🌹","🪻","🌱","🌿","🍀",
        "🍁","🍂","🍃","🌴","🌵",

        // 🍔 Еда
        "🍔","🍟","🍕","🌭","🍿","🥐","🥯","🥞",
        "🍣","🍤","🍱","🥗",
        "🍓","🍒","🍎","🍑","🍉","🍇","🥝","🍍",
        "🍰","🧁","🍪","🍩",

        // 🎨 Хобби
        "🎨","🎧","🎵","🎶","🎸","🎤","🎮","🎲","🧩",
        "🎬","📸","📷","📹",

        // 📚 Учёба / работа
        "📚","📘","📙","📗","📕","📖","📝","🖊️","✏️","📎",
        "📌","📍","📅","📂","💼","📁","🗂️",

        // 🛍️ Покупки / стиль
        "🛍️","👗","👚","👕","👟","👠","💄","💍","💎",

        // 🏠 Дом
        "🏠","🛋️","🛏️","🪑","🚪","🪟","🛁","🚿","🧺",

        // ✈️ Путешествия
        "✈️","🚗","🚕","🚙","🛵","🏍️","🚲","🚉","🗺️","🏕️",
        "🏖️","🏝️","🏔️",

        // 💻 Технологии
        "💻","🖥️","📱","⌨️","🖱️","💽","💾","📀",
        "📡","🔌","🔋",

        // 🔑 Разное
        "🔑","🔒","🔓","🧸","🪄","🎯","🏆","⚽","🏀","🎳",
        "💡","🔮","🕯️","📦","🧭",

        // 🙂 Символы
        "✔️","✖️","➕","➖","➗","⭕","❗","❓","❕","❔",
    )

    val listState = rememberLazyGridState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White.copy(alpha = 0.6f))
    ) {
        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Adaptive(40.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(emojis) { emoji ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            if (selectedIcon == emoji)
                                Color(0xFFFFC1E0)
                            else
                                Color(0xFFFFC1E0).copy(alpha = 0.25f),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onSelect(emoji) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(emoji, fontSize = 24.sp)
                }
            }
        }
    }
}

