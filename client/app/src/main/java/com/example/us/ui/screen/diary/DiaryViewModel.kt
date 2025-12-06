package com.example.us.ui.screen.diary

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DiaryViewModel(private val context: Application) : AndroidViewModel(context) {

    private val storage = DiaryStorage(context)

    var events = mutableStateListOf<DiaryEvent>()
        private set

    init {
        viewModelScope.launch {
            val loaded = storage.loadEvents()
            if (loaded.isNotEmpty()) {
                events.addAll(loaded.sortedByDescending { it.date })
            } else {
                // только если пусто — кладём тестовые данные
                events.addAll(sampleEvents())
                save() // сохраним тестовые события
            }
        }
    }

    private fun sampleEvents(): List<DiaryEvent> {
        val now = System.currentTimeMillis()
        return listOf(
            DiaryEvent(
                id = 1,
                title = "Первое свидание",
                description = "Самый тёплый вечер — долгий разговор, прогулка и смех.",
                date = now - 1000L * 60 * 60 * 24 * 241,
                imageUri = null,
                type = EventType.ROMANTIC
            ),
            DiaryEvent(
                id = 2,
                title = "Подарок",
                description = "Очень трогательный момент, когда подарили маленький сюрприз.",
                date = now - 1000L * 60 * 60 * 24 * 70,
                imageUri = null,
                type = EventType.HAPPY
            )
        )
    }

    fun addEvent(ev: DiaryEvent) {
        events.add(0, ev)
        save()
    }

    fun updateEvent(newEvent: DiaryEvent) {
        val idx = events.indexOfFirst { it.id == newEvent.id }
        if (idx != -1) {
            events[idx] = newEvent
            save()
        }
    }

    private fun save() {
        viewModelScope.launch {
            try {
                storage.saveEvents(events.toList())
            } catch (_: Exception) {
                // можно логировать, но не падать
            }
        }
    }

    fun deleteEvent(id: Int) {
        val idx = events.indexOfFirst { it.id == id }
        if (idx != -1) {
            events.removeAt(idx)
            save()
        }
    }
}
