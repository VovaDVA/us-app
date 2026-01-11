import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.us.R
import com.example.us.ui.component.BottomMenu
import com.example.us.ui.component.TabItem
import com.example.us.ui.screen.calendar.CalendarScreen
import com.example.us.ui.screen.diary.DiaryTimelineScreen
import com.example.us.ui.screen.diary.DiaryViewModel
import com.example.us.ui.screen.games.GamesScreen
import com.example.us.ui.screen.home.HomeScreen
import com.example.us.ui.screen.special.WishesScreen
import com.example.us.ui.screen.special.WishesViewModel

@Composable
fun UsApp() {

    // ---------- ICONS ----------
    val HeartIcon: @Composable () -> Unit = {
        Image(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    }
    val DiaryIcon: @Composable () -> Unit = {
        Image(
            painter = painterResource(id = R.drawable.ic_diary),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    }
    val CalendarIcon: @Composable () -> Unit = {
        Image(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    }
    val StarIcon: @Composable () -> Unit = {
        Image(
            painter = painterResource(id = R.drawable.ic_gift),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    }
    val PuzzleIcon: @Composable () -> Unit = {
        Image(
            painter = painterResource(id = R.drawable.ic_puzzle),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    }

    // ---------- TABS ----------
    val tabs = listOf(
        TabItem("Главная", HeartIcon),
        TabItem("Дневник", DiaryIcon),
        TabItem("Календарь", CalendarIcon),
        TabItem("Особенное", StarIcon),
        TabItem("Игры", PuzzleIcon)
    )

    var currentTab by remember { mutableStateOf(0) }
    var bottomBarVisible by remember { mutableStateOf(true) }

    // ViewModels создаём ЗДЕСЬ, один раз
    val diaryVM: DiaryViewModel = viewModel()
    val wishesVM: WishesViewModel = viewModel()

    Box(Modifier.fillMaxSize()) {

        when (currentTab) {
            0 -> HomeScreen()
            1 -> DiaryTimelineScreen(viewModel = diaryVM)
            2 -> CalendarScreen()
            3 -> WishesScreen(viewModel = wishesVM)
            4 -> GamesScreen(
                onOpenHeartClicker = { },
                hideBottomBar = { visible -> bottomBarVisible = !visible }
            )
        }

        if (bottomBarVisible) {
            BottomMenu(
                tabs = tabs,
                selectedIndex = currentTab,
                onTabSelected = { currentTab = it },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
//
//    // ---------- PAGER ----------
//    val pagerState = rememberPagerState(initialPage = 0) { tabs.size }
//
//    // следим за изменением "вкладки" с меню и двигаем pager
//    var pendingPage by remember { mutableIntStateOf(-1) }
//
//    if (pendingPage != -1) {
//        LaunchedEffect(pendingPage) {
//            pagerState.animateScrollToPage(pendingPage)
//            pendingPage = -1
//        }
//    }
//
//    var bottomBarVisible by remember { mutableStateOf(true) }
//
//
//    // ---------- UI ----------
//    Box(Modifier.fillMaxSize()) {
//
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier.fillMaxSize(),
//            beyondViewportPageCount = 1
//        ) { page ->
//            key(page) {
//                val diaryVM: DiaryViewModel = viewModel()
//
//                when (page) {
//                    0 -> HomeScreen()
//                    1 -> DiaryTimelineScreen(
//                        viewModel = diaryVM
//                    )
//                    2 -> CalendarScreen()
//                    4 -> GamesScreen(
//                        onOpenHeartClicker = { /* open via pager or nav */ },
//                        hideBottomBar = { visible -> bottomBarVisible = !visible }
//                    )
//                    else -> PlaceholderScreen(tabs[page].title)
//                }
//            }
//        }
//
//        if (bottomBarVisible) {
//            BottomMenu(
//                tabs = tabs,
//                selectedIndex = pagerState.currentPage,
//                onTabSelected = { page ->
//                    pendingPage = page     // <-- анимация запустится при следующем recomposition
//                },
//                modifier = Modifier.align(Alignment.BottomCenter)
//            )
//        }
//    }
}
