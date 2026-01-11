package com.example.us.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.us.ui.component.AnimatedLoveBackground
import com.example.us.ui.screen.home.components.MoodCard
import com.example.us.ui.screen.home.components.NextEventCard
import com.example.us.ui.screen.home.components.Quote
import com.example.us.ui.screen.home.components.QuoteBlock
import com.example.us.ui.screen.home.components.TimeTogetherCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val quotes = listOf(
        Quote(
            text = "Любовь — единственная страсть, не признающая ни прошлого, ни будущего",
            author = "Оноре де Бальзак"
        ),
        Quote(
            text = "Любовь — это состояние, в котором счастье другого человека становится важнее твоего собственного",
            author = "Роберт А. Хайнлайн"
        ),
        Quote(
            text = "Любовь — это когда ты в ком-то находишь себя",
            author = "Фридрих Ницше"
        ),
        Quote(
            text = "Любовь есть жизнь. Всё, всё, что я понимаю, я понимаю только потому, что люблю",
            author = "Лев Толстой"
        ),
        Quote(
            text = "Любить — это находить в счастье другого своё собственное счастье",
            author = "Готфрид Вильгельм Лейбниц"
        ),
        Quote(
            text = "Любовь — это два одиночества, которые приветствуют друг друга, соприкасаются и защищают друг друга",
            author = "Райнер Мария Рильке"
        ),
        Quote(
            text = "Когда мы любим и любимы, жизнь наполняется светом и красками",
            author = "Сью Джонсон"
        )
    )

    val currentQuote = remember { quotes.random() }

    val pullState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = viewModel::refresh
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFFFB7D4)
            )
            .pullRefresh(pullState)
    ) {

        // 🌸 красивый живой фон
        AnimatedLoveBackground(Modifier.fillMaxSize())

        // 🔥 вертикальный скролл
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                .navigationBarsPadding(),
//                .padding(bottom = 100.dp), // чтобы не залезало под меню
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 10.dp, bottom = 50.dp)
        ) {
            item { HomeHeader() }

            item {
                TimeTogetherCard()
            }

            item {
                MoodCard(
                    mood = viewModel.mood,
                    onMoodSelected = viewModel::updateMood
                )
            }

            item {
                NextEventCard(event = viewModel.nextEvent)
            }

            item {
                QuoteBlock(currentQuote)
            }

            item {
                Spacer(Modifier.height(40.dp))
            }
        }

        PullRefreshIndicator(
            refreshing = viewModel.isRefreshing,
            state = pullState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
