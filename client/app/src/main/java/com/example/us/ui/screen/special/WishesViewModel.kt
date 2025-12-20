package com.example.us.ui.screen.special

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.us.api.CacheClient
import com.example.us.api.WishApi
import com.example.us.ui.screen.special.classes.AddWishRequest
import com.example.us.ui.screen.special.classes.Wish
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishesViewModel(app: Application) : AndroidViewModel(app) {
    private val scope = CoroutineScope(Dispatchers.IO)

    var myWishes = mutableStateListOf<Wish>()
        private set
    var partnerWishes = mutableStateListOf<Wish>()
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    // Хранит текущего пользователя
    private var currentUserId: Long = 2
    private var currentPartnerId: Long = 1

    init {
        // Подгружаем userId из кеша
        scope.launch {
            currentUserId = CacheClient.get<Long>("userId") ?: 2
            currentPartnerId = CacheClient.get<Long>("partnerId") ?: 1

            CacheClient.get<List<Wish>>("myWishes")?.let { cached ->
                myWishes.addAll(cached)
            }
            CacheClient.get<List<Wish>>("partnerWishes")?.let { cached ->
                partnerWishes.addAll(cached)
            }

            refreshMyWishes()
            refreshPartnerWishes()
            // Если кеш пустой, подгружаем с сервера
//            if (myWishes.isEmpty()) refreshMyWishes()
//            if (partnerWishes.isEmpty()) refreshPartnerWishes()
        }
    }

    fun refreshMyWishes() {
        scope.launch {
            try {
                val wishes = WishApi.myWishes(currentUserId)
                myWishes.clear()
                myWishes.addAll(wishes)
                CacheClient.set("myWishes", wishes)
            } catch (_: Exception) {}
        }
    }

    fun refreshPartnerWishes() {
        scope.launch {
            try {
                val wishes = WishApi.partnerWishes(currentPartnerId)
                partnerWishes.clear()
                partnerWishes.addAll(wishes)
                CacheClient.set("partnerWishes", wishes)
            } catch (_: Exception) {}
        }
    }

    fun addMyWish(wish: Wish) {
        scope.launch {
            try {
                val created = WishApi.createWish(currentUserId, wish)
                myWishes.add(created)
                CacheClient.set("myWishes", myWishes.toList())
            } catch (e: Exception) {
                errorMessage = "Ошибка при создании желания: ${e.message}"
            }
        }
    }


    fun updateWish(wish: Wish) {
        val index = myWishes.indexOfFirst { it.id == wish.id }
        if (index != -1) {
            scope.launch {
                try {
                    WishApi.updateWish(currentUserId, wish.id, wish)
                    myWishes[index] = wish
                    CacheClient.set("myWishes", myWishes.toList())
                } catch (_: Exception) {}
            }
        }
    }

    fun removeWish(id: Long) {
        val index = myWishes.indexOfFirst { it.id == id }
        if (index != -1) {
            myWishes.removeAt(index)
            scope.launch {
                CacheClient.set("myWishes", myWishes.toList())
                try {
                    WishApi.deleteWish(id)
                } catch (_: Exception) {}
            }
        }
    }


    fun toggleDone(id: Long) {
        val index = myWishes.indexOfFirst { it.id == id }
        if (index != -1) {
            val w = myWishes[index].copy(isDone = !myWishes[index].isDone)
            myWishes[index] = w
            scope.launch {
                CacheClient.set("myWishes", myWishes.toList())
                try {
                    WishApi.toggleDone(currentUserId, id)
                } catch (_: Exception) {}
            }
        }
    }

    fun toggleFavorite(id: Long) {
        val index = myWishes.indexOfFirst { it.id == id }
        if (index != -1) {
            val w = myWishes[index].copy(isFavorite = !myWishes[index].isFavorite)
            myWishes[index] = w
            scope.launch {
                CacheClient.set("myWishes", myWishes.toList())
                try {
                    WishApi.toggleFavorite(currentUserId, id)
                } catch (_: Exception) {}
            }
        }
    }
}
