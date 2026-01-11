package com.example.us.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

object AuthStorage {
    private val Context.dataStore by preferencesDataStore(name = "auth")

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val USER_ID_KEY = longPreferencesKey("user_id")
    private val PARTNER_ID_KEY = longPreferencesKey("partner_id")
    private val NAME_KEY = stringPreferencesKey("name")

    suspend fun saveAuth(
        context: Context,
        token: String,
        userId: Long,
        partnerId: Long?,
        name: String
    ) {
        context.dataStore.edit {
            it[TOKEN_KEY] = token
            it[USER_ID_KEY] = userId
            partnerId?.let { pid -> it[PARTNER_ID_KEY] = pid }
            it[NAME_KEY] = name
        }
    }

    fun tokenFlow(context: Context) =
        context.dataStore.data.map { it[TOKEN_KEY] }
}
