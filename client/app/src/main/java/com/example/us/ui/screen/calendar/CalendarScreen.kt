package com.example.us.ui.screen.calendar

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.us.ui.screen.calendar.components.AddEventSheet
import com.example.us.ui.screen.calendar.components.AnimatedMonthTitle
import com.example.us.ui.screen.calendar.components.DayCell
import com.example.us.ui.screen.calendar.components.DaysOfWeekTitle
import com.example.us.ui.screen.calendar.components.EventCard
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.yearMonth
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.us.ui.component.AnimatedLoveBackground
import com.kizitonwose.calendar.core.CalendarDay


val COLOR_PINK = Color(0xFFFF6BAB)
val COLOR_WHITE = Color.White.copy(alpha = 0.85f)

@SuppressLint(
    "CoroutineCreationDuringComposition", "UnrememberedMutableState",
    "FrequentlyChangingValue"
)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun CalendarScreen() {

    val vm: CalendarViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory(LocalContext.current.applicationContext as Application))

    var selectedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var editingIndex by remember { mutableStateOf<Int?>(null) }
    var editingText by remember { mutableStateOf("") }
    var deleteIndex by remember { mutableStateOf<Int?>(null) }
    var editingIcon by remember { mutableStateOf<String?>(null) }

    // Pull refresh ----------------------------------------/
    val isRefreshing by vm.isRefreshing.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { vm.refresh() }
    )
    // -----------------------------------------------------/


    // События для выбранного дня
    val eventsForDay = remember(selectedDate, vm.events) {
        vm.events[selectedDate].orEmpty()
    }

    // Настройки календаря
    val daysOfWeek = remember { daysOfWeek() }
    val currentMonth = remember { YearMonth.now() }

    val calendarState = rememberCalendarState(
        startMonth = currentMonth.minusMonths(12),
        endMonth = currentMonth.plusMonths(12),
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    /* ------ АНИМАЦИЯ ВЫСОТЫ КАЛЕНДАРЯ ------ */

    val listState = rememberLazyListState()
    var isCollapsed by remember { mutableStateOf(false) }


    /* ------ BottomSheet (Добавление/редактирование) ------ */

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val sheetScope = rememberCoroutineScope()
    var sheetVisible by remember { mutableStateOf(false) }

    val openSheet = { sheetVisible = true; sheetScope.launch { sheetState.show() } }
    val closeSheet: () -> Unit = {
        sheetScope.launch { sheetState.hide() }
            .invokeOnCompletion { sheetVisible = false }
    }

    /* ---------------------------------------------------
       UI
    --------------------------------------------------- */
    AnimatedLoveBackground(Modifier.fillMaxSize())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
//            .nestedScroll(nestedScrollConnection)
            .pullRefresh(pullRefreshState)
    ) {

        Column {

            // Заголовок месяца c анимацией
            AnimatedMonthTitle(calendarState.firstVisibleMonth.yearMonth)

            // Календарь
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
            ) {
                HorizontalCalendar(
                    state = calendarState,
                    monthHeader = { DaysOfWeekTitle(daysOfWeek) },
                    dayContent = { day ->
                        val hasEvents = vm.events[day.date]?.isNotEmpty() == true
                        DayCell(
                            day = day,
                            isSelected = (day.date == selectedDate),
                            hasEvents = hasEvents,
                            onClick = { selectedDate = day.date }
                        )
                    },
                    modifier = Modifier.padding(15.dp)
                )
            }

            // Список событий
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp)
                    .padding(top = 10.dp, bottom = 90.dp)
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 90.dp)
            ) {
                itemsIndexed(eventsForDay) { index, event ->
                    EventCard(
                        text = event.text,
                        icon = event.icon,
                        date = selectedDate,
                        onDelete = { deleteIndex = index },
                        onEdit = {
                            editingIndex = index
                            editingText = event.text
                            editingIcon = event.icon
                            openSheet()
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }

        /* FloatingActionButton ---------------------- */
        FloatingActionButton(
            onClick = { openSheet() },
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

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        /* Bottom Sheet ---------------------- */

        if (sheetVisible) {
            ModalBottomSheet(
                onDismissRequest = closeSheet,
                sheetState = sheetState,
                dragHandle = { BottomSheetDefaults.DragHandle(color = COLOR_PINK) }
            ) {
                AddEventSheet(
                    initialText = editingText.takeIf { editingIndex != null },
                    initialIcon = editingIcon ?: "🎀",
                    isEditing = editingIndex != null,
                    onConfirm = { text, icon ->
                        if (editingIndex == null) {
                            vm.add(selectedDate, text, icon)
                        } else {
                            vm.update(selectedDate, editingIndex!!, text, icon)
                        }
                        editingIndex = null
                        editingIcon = null
                        editingText = ""
                        closeSheet()
                    },
                    onClose = {
                        editingIndex = null
                        editingIcon = null
                        editingText = ""
                        closeSheet()
                    }
                )
            }
        }

        deleteIndex?.let { idx ->
            AlertDialog(
                onDismissRequest = { deleteIndex = null },
                title = { Text(text = "Удалить событие?", fontWeight = FontWeight.Bold) },
                text = { Text("Ты точно хочешь удалить событие? Подумай, будет ли это правильным решением.") },
                icon = {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = COLOR_PINK)
                },
                containerColor = Color(0xFFFFF1F9),
                titleContentColor = COLOR_PINK,
                modifier = Modifier.align(Alignment.Center),
                confirmButton = {
                    Button(
                        onClick = {
                            vm.remove(selectedDate, idx)
                            deleteIndex = null
                        },
                        colors = ButtonColors(
                            containerColor = COLOR_PINK,
                            contentColor = Color.White,
                            disabledContainerColor = COLOR_PINK,
                            disabledContentColor = Color.White
                        )
                    ) { Text("Удалить") }
                },
                dismissButton = {
                    FilledTonalButton(
                        onClick = { deleteIndex = null },
                        colors = ButtonColors(
                            containerColor = COLOR_PINK.copy(alpha = 0.1f),
                            contentColor = COLOR_PINK,
                            disabledContainerColor = COLOR_PINK.copy(alpha = 0.1f),
                            disabledContentColor = COLOR_PINK
                        )
                    ) {
                        Text("Нинада")
                    }
                }
            )
        }
    }
}

@Stable
data class DayUiState(
    val date: LocalDate,
    val isSelected: Boolean,
    val hasEvents: Boolean
)

@Composable
fun rememberDayState(day: CalendarDay, selected: LocalDate, store: EventsStore): DayUiState {
    val events = store.events[day.date]
    return remember(day.date, selected, events) {
        DayUiState(
            date = day.date,
            isSelected = day.date == selected,
            hasEvents = events?.isNotEmpty() == true
        )
    }
}
