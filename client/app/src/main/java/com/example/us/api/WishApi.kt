package com.example.us.api

import com.example.us.ui.screen.special.classes.Wish

object WishApi {

    suspend fun myWishes(): List<Wish> =
        ApiClient.get("wish/my")

    suspend fun partnerWishes(): List<Wish> =
        ApiClient.get("wish/partner")

    suspend fun createWish(wish: Wish): Wish =
        ApiClient.post("wish", wish)

    suspend fun updateWish(id: Long, wish: Wish): Wish =
        ApiClient.put("wish/$id", wish)

    suspend fun toggleFavorite(id: Long): Wish =
        ApiClient.post("wish/$id/favorite")

    suspend fun toggleDone(id: Long): Wish =
        ApiClient.post("wish/$id/done")

    suspend fun deleteWish(id: Long) {
        ApiClient.delete<Unit>("wish/$id")
    }
}

