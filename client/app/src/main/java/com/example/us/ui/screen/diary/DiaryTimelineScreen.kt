package com.example.us.ui.screen.diary

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.us.ui.component.AnimatedLoveBackground
import com.example.us.ui.screen.calendar.COLOR_PINK
import com.example.us.ui.screen.calendar.COLOR_WHITE
import java.util.Locale

// --------------------------- Основной экран дневника ---------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryTimelineScreen(viewModel: DiaryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val events = viewModel.events // SnapshotStateList<DiaryEvent>
    var selectedEvent by remember { mutableStateOf<DiaryEvent?>(null) }
    var showAdd by remember { mutableStateOf(false) }
    var editEvent by remember { mutableStateOf<DiaryEvent?>(null) }

    AnimatedLoveBackground(Modifier.fillMaxSize())

    // фон
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .padding(
                top = WindowInsets.statusBars.asPaddingValues()
                    .calculateTopPadding() + 10.dp
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = COLOR_PINK,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(12.dp)
                .zIndex(1000f)
        ) {
            Text(
                text = "Наша история",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Контент — таймлайн
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp), // оставляем место под заголовок
            contentPadding = PaddingValues(
                top = 50.dp,
                bottom = 140.dp
            ), // место для FAB + нижнего меню
        ) {
            itemsIndexed(events) { index, event ->
                TimelineRow(
                    event = event,
                    isFirst = index == 0,
                    isLast = index == events.lastIndex,
                    onClick = { selectedEvent = event }
                )
            }
        }

        // Плавающая кнопка добавить — выше нижнего меню, видна всегда
        FloatingActionButton(
            onClick = { showAdd = true },
            containerColor = Color(0xFFFF6BAB),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = WindowInsets.navigationBars.asPaddingValues()
                        .calculateBottomPadding() + 74.dp
                )
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }
    }

    // диалог просмотра
    selectedEvent?.let {
        DiaryEventDialog(
            event = it,
            onDismiss = { selectedEvent = null },
            onEdit = { ev ->
                selectedEvent = null
                editEvent = ev
                showAdd = true // открываем модалку для редактирования
            }
        )
    }

    // модалка добавления простая (можешь заменить на BottomSheet)
    if (showAdd) {
        AddEventModal(
            initialEvent = editEvent,
            onSave = { ev ->
                if (editEvent == null) viewModel.addEvent(ev)
                else viewModel.updateEvent(ev)
                showAdd = false
                editEvent = null
            },
            onDelete = { ev ->
                viewModel.deleteEvent(ev.id)
                editEvent = null
            },
            onDismiss = {
                showAdd = false
                editEvent = null
            }
        )
    }
}

// --------------------------- Один пункт таймлайна (строка) ---------------------------
@Composable
fun TimelineRow(
    event: DiaryEvent,
    isFirst: Boolean,
    isLast: Boolean,
    onClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Левая колонка: линия + точка
        Column(
            modifier = Modifier
                .width(24.dp)
                .padding(end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // линия сверху (если не первый)
            Canvas(
                modifier = Modifier
                    .height(25.dp)
                    .width(2.dp)
            ) {
                if (!isFirst) {
                    drawLine(
                        color = COLOR_PINK,
                        start = androidx.compose.ui.geometry.Offset(size.width / 2f, size.height),
                        end = androidx.compose.ui.geometry.Offset(size.width / 2f, 0f),
                        strokeWidth = size.width,
                        cap = StrokeCap.Round
                    )
                }
            }

            // кружок (точка)
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(COLOR_PINK)
            )

            // линия снизу (если не последний)
            Canvas(
                modifier = Modifier
                    .height(80.dp) // будет обрезан контейнером; используем weight-альтернативу — но проще: небольшой отрезок
                    .width(2.dp)
            ) {
                if (!isLast) {
                    drawLine(
                        color = COLOR_PINK,
                        start = androidx.compose.ui.geometry.Offset(size.width / 2f, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width / 2f, size.height),
                        strokeWidth = size.width,
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        // Карточка события
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(top = 10.dp),
            colors = CardDefaults.cardColors(containerColor = COLOR_WHITE),
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
            ) {
                // мини-картинка
                if (event.imageUri != null) {
                    AsyncImage(
                        model = event.imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .size(75.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(COLOR_PINK.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = event.title.take(1).uppercase(Locale.getDefault()),
                            color = COLOR_PINK,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .height(75.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // дата — дни назад
                    val daysAgo = ((System.currentTimeMillis() - event.date) / 86_400_000L).toInt()

                    Text(
                        text = event.title,
                        fontSize = 20.sp,
                        color = Color(0xFF2B2025),
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = event.description.take(120),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6A5A63),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = if (daysAgo == 0) "Сегодня" else "$daysAgo д. назад",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF9A7A88)
                        )

                        Box(
                            modifier = Modifier
                                .background(
                                    eventColor(event.type).copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = eventTypeRussian(event.type).lowercase()
                                    .replaceFirstChar { it.uppercase() },
                                color = eventColor(event.type),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}
