package com.example.us.ui.screen.special

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateListOf
import com.example.us.ui.screen.special.classes.Wish
import com.example.us.ui.screen.special.classes.WishesStore

class WishesViewModel(app: Application) : AndroidViewModel(app) {
    private val store = WishesStore(app.applicationContext)

    var myWishes = mutableStateListOf<Wish>()
        private set
    var partnerWishes = mutableStateListOf<Wish>()
        private set

    init {
        myWishes.addAll(store.wishes)
        // тестовые желания партнёра
        partnerWishes.addAll(listOf(
            Wish(text = "Книга по фотографии", description = "Хочу купить новую книгу", isFavorite = true, link = "https://www.wildberries.ru/catalog/536636684/detail.aspx?size=739419353&targetUrl=MI%7C-1%7CWTL%7CIT%7C%7C%7C%7C%7C%7C%7C%7C%7C%7C%7C", categoryIcon = "📚"),
            Wish(text = "Новый плед", description = "", categoryIcon = "🛋️"),
            Wish(text = "Романтический ужин", description = "В ресторане у озера", categoryIcon = "🍽️"),
            Wish(text = "Курс рисования", description = "Онлайн курс", link = "https://example.com/art", categoryIcon = "🎨")
        ))
    }

    fun addMyWish(wish: Wish) {
        myWishes.add(wish)
        store.add(wish)
    }

    fun updateWish(wish: Wish) {
        val index = myWishes.indexOfFirst { it.id == wish.id }
        if (index != -1) {
            myWishes[index] = wish
            store.update(wish)
        }
    }

    fun removeWish(id: Long) {
        myWishes.removeAll { it.id == id }
        store.remove(id)
    }

    fun toggleDone(id: Long) {
        val index = myWishes.indexOfFirst { it.id == id }
        if (index != -1) {
            val w = myWishes[index]
            myWishes[index] = w.copy(isDone = !w.isDone)
            store.update(myWishes[index])
        }
    }

    fun toggleFavorite(id: Long) {
        val index = myWishes.indexOfFirst { it.id == id }
        if (index != -1) {
            val w = myWishes[index]
            myWishes[index] = w.copy(isFavorite = !w.isFavorite)
            store.update(myWishes[index])
        }
    }
}
