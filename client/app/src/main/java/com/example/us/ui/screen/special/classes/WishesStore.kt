package com.example.us.ui.screen.special.classes

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.us.ui.screen.special.classes.Wish
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

class WishesStore(private val context: Context) {
    private val file = File(context.filesDir, "wishes.json")

    var wishes by mutableStateOf<List<Wish>>(emptyList())
        private set

    init { load() }

    fun add(wish: Wish) {
        wishes = wishes + wish
        save()
    }

    fun update(wish: Wish) {
        wishes = wishes.map { if (it.id == wish.id) wish else it }
        save()
    }

    fun remove(id: Long) {
        wishes = wishes.filter { it.id != id }
        save()
    }

    private fun save() {
        file.writeText(Json.encodeToString(wishes))
    }

    private fun load() {
        wishes = if (file.exists()) {
            try { Json.decodeFromString<List<Wish>>(file.readText()) } catch (_: Exception) { emptyList() }
        } else emptyList()
    }
}
