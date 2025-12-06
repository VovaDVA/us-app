package com.example.us.ui.screen.calendar

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDate

@Serializable
data class EventItem(
    val text: String,
    val icon: String = "🎀"
)


class EventsStore(private val context: Context) {
    private val file: File get() = File(context.filesDir, "events.json")

    var events by mutableStateOf<Map<LocalDate, MutableList<EventItem>>>(emptyMap())
        private set

    init {
        load()
    }

    fun add(date: LocalDate, text: String, icon: String = "🎀") {
        val updated = events.toMutableMap()
        val list = updated[date] ?: mutableListOf()
        list.add(EventItem(text, icon))
        updated[date] = list
        events = updated
        save()
    }

    fun update(date: LocalDate, index: Int, newText: String, newIcon: String) {
        val list = events[date]?.toMutableList() ?: return
        if (index in list.indices) {
            list[index] = EventItem(newText, newIcon)
            events = events.toMutableMap().also { it[date] = list }
            save()
        }
    }

    fun remove(date: LocalDate, index: Int) {
        val list = events[date]?.toMutableList() ?: return
        if (index in list.indices) {
            list.removeAt(index)
            events = events.toMutableMap().also { it[date] = list }
            save()
        }
    }

    private fun save() {
        val serializable = events.mapValues { entry ->
            entry.value.map { it } // EventItem сериализуется через kotlinx.serialization
        }.mapKeys { it.key.toString() }
        file.writeText(Json.encodeToString(serializable))
    }

    private fun load() {
        if (!file.exists()) {
            events = emptyMap()
            return
        }

        try {
            val text = file.readText()
            val map: Map<String, List<EventItem>> = Json.decodeFromString(text)
            val restored = mutableMapOf<LocalDate, MutableList<EventItem>>()

            map.forEach { (k, v) ->
                runCatching { LocalDate.parse(k) }
                    .getOrNull()
                    ?.let { restored[it] = v.toMutableList() }
            }

            events = restored
        } catch (_: Exception) {
            events = emptyMap()
        }
    }
}
