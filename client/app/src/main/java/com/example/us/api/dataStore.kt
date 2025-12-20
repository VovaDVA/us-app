package com.example.us.api

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException

val Context.dataStore by preferencesDataStore("us_cache")

object CacheClient {

    lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    // --- универсальный SET ---
    suspend inline fun <reified T> set(key: String, value: T) {
        val json = Json.encodeToString(value)
        val prefKey = stringPreferencesKey(key)

        appContext.dataStore.edit { prefs ->
            prefs[prefKey] = json
        }
    }

    // --- универсальный GET ---
    suspend inline fun <reified T> get(key: String): T? {
        val prefKey = stringPreferencesKey(key)

        val json = appContext.dataStore.data
            .catch {
                if (it is IOException) emit(emptyPreferences())
                else throw it
            }
            .map { prefs -> prefs[prefKey] }
            .first() ?: return null

        return Json.decodeFromString<T>(json)
    }

    // --- DELETE ---
    suspend fun remove(key: String) {
        val prefKey = stringPreferencesKey(key)
        appContext.dataStore.edit { prefs ->
            prefs.remove(prefKey)
        }
    }
}
