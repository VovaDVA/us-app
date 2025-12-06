package com.example.us.ui.screen.diary

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.diaryDataStore: DataStore<Preferences> by preferencesDataStore("diary_events")

class DiaryStorage(private val context: Context) {

    private val KEY_EVENTS = stringPreferencesKey("events_json")
    private val gson = Gson()

    suspend fun loadEvents(): List<DiaryEvent> {
        val json = context.diaryDataStore.data.map { it[KEY_EVENTS] ?: "" }.first()
        if (json.isBlank()) return emptyList()
        return try {
            gson.fromJson(json, object : TypeToken<List<DiaryEvent>>() {}.type)
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun saveEvents(list: List<DiaryEvent>) {
        val json = gson.toJson(list)
        context.diaryDataStore.edit { it[KEY_EVENTS] = json }
    }
}
