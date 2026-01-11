package com.example.us.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.us.api.ApiClient
import com.example.us.api.CacheClient
import com.example.us.api.EventApi
import com.example.us.ui.screen.calendar.CacheClientCalendar
import com.example.us.ui.screen.calendar.NextEventDto
import com.example.us.ui.screen.home.utils.MoodType
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var loadJob: Job? = null

    var isRefreshing by mutableStateOf(false)
        private set

    var mood by mutableStateOf<MoodType?>(null)
        private set

    var nextEvent by mutableStateOf<NextEventDto?>(null)
        private set

    init {
        load()
    }

    fun refresh() {
        load(force = true)
    }

    private fun load(force: Boolean = false) {
        if (loadJob?.isActive == true) return

        loadJob = viewModelScope.launch {
            isRefreshing = true
            try {
                loadMood(force)
                loadNextEvent(force)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isRefreshing = false
            }
        }
    }

    private suspend fun loadMood(force: Boolean) {
        val cached: MoodType? =
            if (!force) CacheClient.get("mood_user") else null

        if (cached != null) {
            mood = cached
        } else {
            val fromServer: MoodType = ApiClient.get("users/mood")
            mood = fromServer
            CacheClient.set("mood_user", fromServer)
        }
    }

    private suspend fun loadNextEvent(force: Boolean) {
        val cached =
            if (!force) CacheClientCalendar.loadNextEvent() else null

        if (cached != null) {
            nextEvent = cached
        } else {
            val fetched = EventApi.getNext()
            if (fetched != null) {
                nextEvent = fetched
                CacheClientCalendar.saveNextEvent(fetched)
            } else {
                nextEvent = null
            }
        }
    }

    fun updateMood(newMood: MoodType) {
        mood = newMood
        viewModelScope.launch {
            try {
                ApiClient.put<Unit>("users/mood", newMood)
                CacheClient.set("mood_user", newMood)
            } catch (e: Exception) {
                // по-хорошему тут лог + откат
            }
        }
    }
}

