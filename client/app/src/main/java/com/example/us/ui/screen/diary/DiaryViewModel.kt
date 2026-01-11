package com.example.us.ui.screen.diary

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.us.api.ApiClient
import com.example.us.api.DiaryApi
import com.example.us.api.FileApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiaryViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val storage = DiaryStorage(app)

    var events = mutableStateListOf<DiaryEvent>()
        private set

    init {
        load(false)
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            load(true)
            _isRefreshing.value = false
        }
    }

    fun load(ignoreCach: Boolean = false) {
        viewModelScope.launch {
            if (!ignoreCach) {
                // 1. грузим из кеша
                val cached = storage.loadEvents()
                if (cached.isNotEmpty()) {
                    events.clear()
                    events.addAll(cached)
                }
            }

            // 2. грузим с сервера
            try {
                val fromServer = DiaryApi.getAll()

                events.clear()
                events.addAll(fromServer.sortedByDescending { it.date })

                storage.saveEvents(fromServer)
            } catch (_: Exception) {
                // если сервер упал — работаем с кешем
            }
        }
    }

    fun addEvent(
        draft: DiaryEvent,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            try {
                var smallUrl: String? = null
                var largeUrl: String? = null

                // 1️⃣ Если есть картинка — грузим
                if (imageUri != null) {
                    val uploaded = FileApi.uploadDiaryImage(getApplication(), imageUri)
                    smallUrl = uploaded.smallUrl
                    largeUrl = uploaded.largeUrl
                }

                val base = ApiClient.BASE_URL.trimEnd('/')

                // 2️⃣ Создаём событие уже с URL
                val created = DiaryApi.create(
                    draft.copy(
                        imageSmallUrl = if (smallUrl != null) "$base$smallUrl" else null,
                        imageLargeUrl = if (largeUrl != null) "$base$largeUrl" else null
                    )
                )

                events.add(created)
                storage.saveEvents(events.toList())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateEvent(
        draft: DiaryEvent,
        newImageUri: Uri?
    ) {
        viewModelScope.launch {
            try {
                var smallUrl = draft.imageSmallUrl
                var largeUrl = draft.imageLargeUrl

                // если выбрали новую картинку
                if (newImageUri != null) {
                    val uploaded = FileApi.uploadDiaryImage(getApplication(), newImageUri)
                    smallUrl = uploaded.smallUrl
                    largeUrl = uploaded.largeUrl
                }

                val base = ApiClient.BASE_URL.trimEnd('/')

                val updated = DiaryApi.update(
                    draft.id.toLong(),
                    draft.copy(
                        imageSmallUrl = if (smallUrl != null) "$base$smallUrl" else null,
                        imageLargeUrl = if (largeUrl != null) "$base$largeUrl" else null
                    )
                )

                val idx = events.indexOfFirst { it.id == updated.id }
                if (idx != -1) {
                    events[idx] = updated
                    storage.saveEvents(events.toList())
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteEvent(id: Int) {
        viewModelScope.launch {
            try {
                DiaryApi.delete(id.toLong())
                events.removeAll { it.id == id }
                storage.saveEvents(events.toList())
            } catch (_: Exception) {
            }
        }
    }
}
