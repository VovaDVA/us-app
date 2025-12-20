package com.example.us.ui.screen.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate

class EventsStoreAdapter(private val vm: CalendarViewModel) {
    // expose map in same shape as old store
    var events by mutableStateOf<Map<LocalDate, MutableList<EventItem>>>(emptyMap())
        private set

    init {
        // initial sync
        updateLocalFromVm()
    }

    private fun updateLocalFromVm() {
        // convert EventItemLocal -> EventItem (old type without id)
        val converted =
            vm.events.mapValues { it.value.map { e -> EventItem(e.text, e.icon) }.toMutableList() }
        events = converted
    }

    // call vm methods and then update adapter map
    fun add(date: LocalDate, text: String, icon: String = "🎀") {
        vm.add(date, text, icon)
        // optimistic update
        updateLocalFromVm()
    }

    fun update(date: LocalDate, index: Int, newText: String, newIcon: String) {
        vm.update(date, index, newText, newIcon)
        updateLocalFromVm()
    }

    fun remove(date: LocalDate, index: Int) {
        vm.remove(date, index)
        updateLocalFromVm()
    }
}
