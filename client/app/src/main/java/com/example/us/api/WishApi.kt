package com.example.us.api

import com.example.us.ui.screen.special.classes.Wish

object WishApi {

    // Получить свои желания
    suspend fun myWishes(userId: Long): List<Wish> =
        ApiClient.get("wish/my?userId=$userId")

    // Получить желания партнера
    suspend fun partnerWishes(partnerId: Long): List<Wish> =
        ApiClient.get("wish/partner?partnerId=$partnerId")

    // Создать новое желание
    suspend fun createWish(userId: Long, wish: Wish): Wish =
        ApiClient.post("wish?userId=$userId", wish)

    // Обновить желание (редактировать)
    suspend fun updateWish(userId: Long, id: Long, wish: Wish): Wish =
        ApiClient.put("wish/$id?userId=$userId", wish)

    // Переключить важность
    suspend fun toggleFavorite(userId: Long, id: Long): Wish =
        ApiClient.post("wish/$id/favorite?userId=$userId")

    // Переключить выполнено/не выполнено
    suspend fun toggleDone(userId: Long, id: Long): Wish =
        ApiClient.post("wish/$id/done?userId=$userId")

    suspend fun deleteWish(id: Long): Unit =
        ApiClient.delete("wish/$id")
}
