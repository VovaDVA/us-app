package com.example.us.ui.screen.special.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.special.classes.Wish

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishEditSheet(
    wish: Wish? = null,
    onSave: (Wish) -> Unit,
    onCancel: () -> Unit,
    onDelete: ((Long) -> Unit)? = null
) {
    var text by remember { mutableStateOf(wish?.title ?: "") }
    var description by remember { mutableStateOf(wish?.description ?: "") }
    var link by remember { mutableStateOf(wish?.link ?: "") }
    var isFavorite by remember { mutableStateOf(wish?.isFavorite ?: false) }
    var categoryIcon by remember { mutableStateOf(wish?.categoryIcon ?: "★") }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (wish == null) "Новое желание" else "Редактировать желание",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6A1B4D)
                )
                IconButton(onClick = onCancel) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Color(0xFF6A1B4D))
                }
            }
        }

        item { Spacer(Modifier.height(12.dp)) }

        item {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }

        item { Spacer(Modifier.height(8.dp)) }

        item {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Описание (не обязательно)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }

        item { Spacer(Modifier.height(8.dp)) }

        item {
            OutlinedTextField(
                value = link,
                onValueChange = { link = it },
                label = { Text("Ссылка (не обязательно)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
        }

        item { Spacer(Modifier.height(12.dp)) }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { isFavorite = !isFavorite }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "important",
                        tint = if (isFavorite) Color(0xFFFFC107) else Color(0xFFBDBDBD)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text("Важное", fontSize = 16.sp)
            }
        }

        item { Spacer(Modifier.height(12.dp)) }

        item { Text("Выбери иконку", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
        item { Spacer(Modifier.height(16.dp)) }

        item {
            EmojiGridSelector(
                selectedIcon = categoryIcon,
                onSelect = { categoryIcon = it }
            )
        }

        item { Spacer(Modifier.height(24.dp)) }

        item {
            Button(
                onClick = {
                    onSave(
                        Wish(
                            id = wish?.id ?: System.currentTimeMillis(),
                            title = text,
                            description = description,
                            link = link.ifBlank { null },
                            categoryIcon = categoryIcon,
                            isDone = wish?.isDone ?: false,
                            isFavorite = isFavorite
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFA6D1))
            ) {
                Text(if (wish == null) "Добавить" else "Сохранить")
            }
        }

        if (wish != null && onDelete != null) {
            item { Spacer(Modifier.height(12.dp)) }

            item {
                Button(
                    onClick = { onDelete(wish.id) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B6B))
                ) {
                    Text("Удалить", color = Color.White)
                }
            }
        }
    }
}
