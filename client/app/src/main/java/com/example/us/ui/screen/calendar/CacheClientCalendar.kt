package com.example.us.ui.screen.calendar

import com.example.us.api.CacheClient
import kotlinx.serialization.json.Json

object CacheClientCalendar {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun saveNextEvent(event: NextEventDto?) {
        val str = event?.let { json.encodeToString(it) }
        CacheClient.set("nextEvent", str)
    }

    suspend fun loadNextEvent(): NextEventDto? {
        val str = CacheClient.get<String>("nextEvent") ?: return null
        return runCatching { json.decodeFromString<NextEventDto>(str) }.getOrNull()
    }
}
