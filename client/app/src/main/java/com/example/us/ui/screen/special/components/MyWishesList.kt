package com.example.us.ui.screen.special.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.us.ui.screen.special.WishesViewModel
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

