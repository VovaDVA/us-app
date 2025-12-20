package com.example.us.ui.screen.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.us.api.CacheClient
import com.example.us.api.EventApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDate

class CalendarViewModel(app: Application) : AndroidViewModel(app) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val ctx = app.applicationContext

    private val file: File get() = File(ctx.filesDir, "events.json")
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    var events: Map<LocalDate, MutableList<EventItemLocal>> = emptyMap()
        private set

    private var userId: Long = 2
    private var partnerId: Long = 1

    init {
        scope.launch {
            userId = CacheClient.get<Long>("userId") ?: 2
            partnerId = CacheClient.get<Long>("partnerId") ?: 1

            loadFromFile()
            refreshFromServer()
        }
    }

    // --- sync from server ---
    fun refreshFromServer() {
        scope.launch {
            try {
                val dtoList = EventApi.getAll(userId, partnerId)
                val map = mutableMapOf<LocalDate, MutableList<EventItemLocal>>()
                dtoList.forEach { dto ->
                    val date = LocalDate.parse(dto.date)
                    val list = map.getOrPut(date) { mutableListOf() }
                    list.add(EventItemLocal(id = dto.id, text = dto.text, icon = dto.icon))
                }
                events = map
                saveToFile()
            } catch (_: Exception) {
                // можно логировать или установить error-state
            }
        }
    }

    // --- add ---
    fun add(date: LocalDate, text: String, icon: String = "🎀") {
        scope.launch {
            try {
                val req = EventItemDto(date = date.toString(), text = text, icon = icon)
                val created = EventApi.create(userId, partnerId, req)
                val updated = events.toMutableMap()
                val list = updated[date] ?: mutableListOf()
                list.add(EventItemLocal(id = created.id, text = created.text, icon = created.icon))
                updated[date] = list
                events = updated
                saveToFile()
            } catch (e: Exception) {
                // fallback: добавление локально, чтобы UI отреагировал
                val updated = events.toMutableMap()
                val list = updated[date] ?: mutableListOf()
                list.add(EventItemLocal(id = null, text = text, icon = icon))
                updated[date] = list
                events = updated
                saveToFile()
            }
        }
    }

    // --- update ---
    fun update(date: LocalDate, index: Int, newText: String, newIcon: String) {
        scope.launch {
            val list = events[date]?.toMutableList() ?: return@launch
            if (index !in list.indices) return@launch
            val old = list[index]
            try {
                val id = old.id
                val req = EventItemDto(id = id, date = date.toString(), text = newText, icon = newIcon)
                val updatedDto = if (id != null) {
                    EventApi.update(userId, partnerId, id, req)
                } else {
                    // если у локального элемента нет id — создаём новый на сервере
                    EventApi.create(userId, partnerId, req)
                }
                list[index] = EventItemLocal(id = updatedDto.id, text = updatedDto.text, icon = updatedDto.icon)
                events = events.toMutableMap().also { it[date] = list }
                saveToFile()
            } catch (e: Exception) {
                // локально применим изменения и сохраним
                list[index] = EventItemLocal(id = old.id, text = newText, icon = newIcon)
                events = events.toMutableMap().also { it[date] = list }
                saveToFile()
            }
        }
    }

    // --- remove ---
    fun remove(date: LocalDate, index: Int) {
        scope.launch {
            val list = events[date]?.toMutableList() ?: return@launch
            if (index !in list.indices) return@launch
            val item = list[index]
            try {
                item.id?.let { id ->
                    EventApi.delete(userId, partnerId, id)
                }
                list.removeAt(index)
                events = events.toMutableMap().also { it[date] = list }
                saveToFile()
            } catch (e: Exception) {
                // на случай ошибки — всё равно удалить локально
                list.removeAt(index)
                events = events.toMutableMap().also { it[date] = list }
                saveToFile()
            }
        }
    }

    // --- file persistence (формат: Map<String, List<EventItemLocal>>) ---
    private fun saveToFile() {
        try {
            val serializable = events.mapKeys { it.key.toString() }
                .mapValues { entry -> entry.value.toList() } // List<EventItemLocal>
            file.writeText(json.encodeToString(serializable))
        } catch (_: Exception) { /* ignore */ }
    }

    private fun loadFromFile() {
        if (!file.exists()) {
            events = emptyMap()
            return
        }
        try {
            val text = file.readText()
            // warning: manual decode because generic types; using Json.decodeFromString<Map<String, List<EventItemLocal>>>
            val map: Map<String, List<EventItemLocal>> = json.decodeFromString(text)
            val restored = mutableMapOf<LocalDate, MutableList<EventItemLocal>>()
            map.forEach { (k, v) ->
                runCatching { LocalDate.parse(k) }.getOrNull()?.let { d ->
                    restored[d] = v.toMutableList()
                }
            }
            events = restored
        } catch (_: Exception) {
            events = emptyMap()
        }
    }
}
