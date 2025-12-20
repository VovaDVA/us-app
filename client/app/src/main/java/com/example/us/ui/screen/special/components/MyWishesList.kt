package com.example.us.ui.screen.special.components

import com.example.us.ui.screen.special.WishesViewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.us.ui.screen.special.classes.Wish

@Composable
fun MyWishesList(viewModel: WishesViewModel, onEdit: (Wish) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(), // оставляем место под заголовок
        contentPadding = PaddingValues(
            top = 20.dp,
            bottom = 140.dp
        ),
    ) {
        items(viewModel.myWishes) { wish ->
            WishItem(
                wish = wish,
                onDone = { viewModel.toggleDone(wish.id) },
                onFavorite = { viewModel.toggleFavorite(wish.id) },
                onClick = { onEdit(wish) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

//init {
//    myWishes.addAll(store.wishes)
//    // тестовые желания партнёра
//    partnerWishes.addAll(listOf(
//        Wish(title = "Книга по фотографии", description = "Хочу купить новую книгу", isFavorite = true, link = "https://www.wildberries.ru/catalog/536636684/detail.aspx?size=739419353&targetUrl=MI%7C-1%7CWTL%7CIT%7C%7C%7C%7C%7C%7C%7C%7C%7C%7C%7C", categoryIcon = "📚"),
//        Wish(title = "Новый плед", description = "", categoryIcon = "🛋️"),
//        Wish(title = "Романтический ужин", description = "В ресторане у озера", categoryIcon = "🍽️"),
//        Wish(title = "Курс рисования", description = "Онлайн курс", link = "https://example.com/art", categoryIcon = "🎨")
//    ))
//}

