package com.example.us.ui.screen.games

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HeartClickerStore private constructor(private val dataStore: DataStore<Preferences>) {

    private object Keys {
        val CLICKS = intPreferencesKey("clicks")
        val LAST_LEVEL = intPreferencesKey("last_level")
    }

    val clicksFlow: Flow<Int> = dataStore.data.map { it[Keys.CLICKS] ?: 0 }
    val levelFlow: Flow<Int> = dataStore.data.map { it[Keys.LAST_LEVEL] ?: 0 }

    suspend fun setClicks(value: Int) {
        dataStore.edit { prefs ->
            prefs[Keys.CLICKS] = value
        }
    }

    suspend fun addClicks(delta: Int) {
        dataStore.edit { prefs ->
            prefs[Keys.CLICKS] = (prefs[Keys.CLICKS] ?: 0) + delta
        }
    }

    suspend fun setLevel(level: Int) {
        dataStore.edit { prefs ->
            prefs[Keys.LAST_LEVEL] = level
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HeartClickerStore? = null

        fun getInstance(context: Context): HeartClickerStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: HeartClickerStore(
                    PreferenceDataStoreFactory.create(
                        scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
                        produceFile = { context.preferencesDataStoreFile("heart_clicker.preferences") }
                    )
                ).also { INSTANCE = it }
            }
        }
    }
}
