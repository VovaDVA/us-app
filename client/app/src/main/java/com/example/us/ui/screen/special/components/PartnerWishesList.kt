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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.us.ui.screen.special.WishesViewModel
import com.example.us.ui.screen.special.classes.Wish
import com.example.us.ui.screen.special.classes.openUrl

@Composable
fun PartnerWishesList(
    viewModel: WishesViewModel,
    onShowDetails: (Wish) -> Unit
) {
    val items = viewModel.partnerWishes
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 20.dp, bottom = 140.dp)
    ) {
        items(items) { wish ->
            WishItem(
                wish = wish,
                isOwn = false,
                showLinkIcon = !wish.link.isNullOrBlank(),
                onFavorite = { viewModel.toggleFavorite(wish.id) },
                onClick = {
                    if (!wish.link.isNullOrBlank()) {
                        openUrl(context, wish.link)
                    } else {
                        onShowDetails.invoke(wish) // передаем наружу, чтобы открыть диалог
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

