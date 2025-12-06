import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.us.ui.screen.games.HeartClickerStore

@Composable
fun rememberHeartClickerStore(): HeartClickerStore {
    val ctx = LocalContext.current.applicationContext
    return remember { HeartClickerStore.getInstance(ctx) }
}

