package com.example.us.ui.screen.games

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rememberHeartClickerStore
import kotlin.random.Random

// particle state single definition
data class ParticleState(
    val id: Int,
    val xPx: Animatable<Float, *>,
    val yPx: Animatable<Float, *>,
    val alpha: Animatable<Float, *>,
    val sizeDp: Float
)

data class LightParticle(
    val id: Int,
    val startX: Float,
    val startY: Float,
    val endX: Float,
    val endY: Float,
    val sizeDp: Float,
    val lifetime: Int = 900
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartClickerScreen(
    onBack: () -> Unit,
    hideBottomBar: (Boolean) -> Unit = {}
) {
    // colors
    val pink = Color(0xFFFF6BAB)
    val softBackground = Color(0xFFFFEEF4)
    val cardBackground = Color.White.copy(alpha = 0.95f)
    val darkText = Color(0xFF2B2025)
    var showRanksSheet by remember { mutableStateOf(false) }

    // hide bottom bar while inside game
    SideEffect { hideBottomBar(true) }
    DisposableEffect(Unit) {
        onDispose { hideBottomBar(false) }
    }

    val store = rememberHeartClickerStore()
    val uiScope = rememberCoroutineScope()

    // collect clicks
    val clicks by store.clicksFlow.collectAsState(initial = 0)

    // level / progress
    val levelIndex = HeartRanks.levelForClicks(clicks)
    val levelName = HeartRanks.names.getOrNull(levelIndex) ?: HeartRanks.names.first()
    val nextThreshold = HeartRanks.nextThresholdFor(levelIndex)
    val prevThreshold = HeartRanks.thresholds.getOrNull(levelIndex) ?: 0
    val progressFrac = if (nextThreshold - prevThreshold > 0)
        (clicks - prevThreshold).toFloat() / (nextThreshold - prevThreshold)
    else 1f

    // heart position (px)
    var heartCenterX by remember { mutableStateOf(0f) }
    var heartCenterY by remember { mutableStateOf(0f) }
    var heartSizePx by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    val particles = remember { mutableStateListOf<ParticleState>() }
    val lightParticles = remember { mutableStateListOf<LightParticle>() }

    // Dedicated scope for particles (so cancelling it won't cancel Compose scope)
    val particleScope = remember {
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }
    DisposableEffect(Unit) {
        onDispose {
            particleScope.cancel()
            particles.clear()
        }
    }

    // spring scale for heart
    val scaleAnim = remember { Animatable(1f) }
    val pulse = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            pulse.animateTo(1.07f, tween(900))
            pulse.animateTo(1.0f, tween(900))
        }
    }

    val onHeartTap: () -> Unit = {
        uiScope.launch {
            // press-down instantly, then spring back (elastic)
            scaleAnim.snapTo(0.88f)
            scaleAnim.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = Spring.DampingRatioMediumBouncy
                )
            )
        }

        // store update
        uiScope.launch {
            try {
                store.addClicks(1)
            } catch (_: Exception) {
                // ignore
            }
        }

        // spawn stronger, red particles
//        spawnParticlesNearHeart(
//            count = 16,
//            centerX = heartCenterX,
//            centerY = heartCenterY,
//            particles = particles,
//            scope = particleScope
//        )

        spawnLightParticles(
            count = 12,
            centerX = heartCenterX,
            centerY = heartCenterY,
            particles = lightParticles
        )
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(softBackground)
            .padding(12.dp)
    ) {
        // header
        GamesHeader()

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // stats card (top)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Нажатий", color = darkText.copy(alpha = 0.9f))
                        Text(
                            "$clicks",
                            color = darkText,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Уровень", color = darkText.copy(alpha = 0.7f))
                        Text(
                            levelName,
                            color = darkText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(Modifier.height(22.dp))

            // big heart
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .onGloballyPositioned { coords ->
                        val size = coords.size
                        heartSizePx = size.width
                        // positionInRoot gives top-left; add half to get center
                        val pos = coords.positionInRoot()
                        heartCenterX = pos.x + size.width / 2f
                        heartCenterY = pos.y + size.height / 2f
                    }
                    .graphicsLayer {
                        scaleX = scaleAnim.value * pulse.value
                        scaleY = scaleAnim.value * pulse.value
                        alpha = 0.999f // фикс для рендеринга glow
                    }

                    .clickable { onHeartTap() },
                contentAlignment = Alignment.Center
            ) {
                // под Image — сразу внутри этого Box:
//                Box(
//                    modifier = Modifier
//                        .size(260.dp)
//                        .blur(radius = 20.dp)
//                        .background(Color(0xFFFF6BAB).copy(alpha = 0.45f), RoundedCornerShape(200.dp))
//                )

                Image(
                    painter = painterResource(id = R.drawable.ic_heart_big),
                    contentDescription = "Heart",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(14.dp))
            Text("Тапай по сердцу — собирай любовь", color = darkText.copy(alpha = 0.85f))
        }

        lightParticles.forEach { p ->
            val progress = remember { Animatable(0f) }

            LaunchedEffect(p.id) {
                progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(p.lifetime)
                )
                lightParticles.remove(p)
            }

            val x = lerp(p.startX, p.endX, progress.value)
            val y = lerp(p.startY, p.endY, progress.value)
            val alpha = 1f - progress.value

            Text(
                text = "❤️",
                fontSize = p.sizeDp.sp,
                modifier = Modifier
                    .offset {
                        IntOffset(
                            with(density) { x.toDp().roundToPx() },
                            with(density) { y.toDp().roundToPx() }
                        )
                    }
                    .graphicsLayer(alpha = alpha)
            )
        }

        // particles overlay (absolute coordinates)
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
//            // snapshot to avoid concurrency problems
//            val snapshot = particles.toList()
//            snapshot.forEach { p ->
//                val xDp = with(density) { p.xPx.value.toDp() }
//                val yDp = with(density) { p.yPx.value.toDp() }
//                Text(
//                    text = "❤️",
//                    fontSize = p.sizeDp.sp,
//                    modifier = Modifier
//                        .offset { IntOffset(xDp.roundToPx(), yDp.roundToPx()) }
//                        .graphicsLayer(alpha = p.alpha.value)
//                )
//            }
//        }

        // bottom progress card
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                )
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Статус: $levelName",
                            color = darkText,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = { showRanksSheet = true }) {
                            Icon(
                                Icons.Default.Star, // временно можешь поставить ic_star
                                contentDescription = "Ranks",
                                tint = pink
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = progressFrac.coerceIn(0f, 1f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        color = pink,
                        trackColor = pink.copy(alpha = 0.25f)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "${clicks - prevThreshold} / ${nextThreshold - prevThreshold}",
                        color = darkText
                    )
                }
            }
        }

        // back button
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(12.dp)
                .padding(top = 19.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
    }

    // Список статусов
    if (showRanksSheet) {
        ModalBottomSheet(
            onDismissRequest = { showRanksSheet = false },
            containerColor = Color.White,
            tonalElevation = 4.dp,
            shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Text(
                    "Статусы",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkText
                )

                Spacer(Modifier.height(12.dp))

                LazyColumn {
                    HeartRanks.names.forEachIndexed { index, name ->
                        val threshold = HeartRanks.thresholds.getOrNull(index) ?: 0
                        val next = HeartRanks.thresholds.getOrNull(index + 1)
                        val achieved = clicks >= threshold
                        val isCurrent = index == levelIndex

                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = when {
                                        isCurrent -> pink.copy(alpha = 0.25f)
                                        achieved -> Color(0xFFB5FFC0)
                                        else -> Color.LightGray.copy(alpha = 0.25f)
                                    }
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    Text(
                                        name,
                                        fontSize = 18.sp,
                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                        color = darkText
                                    )

                                    if (isCurrent && next != null) {
                                        val localPrev = threshold
                                        val localNext = next
                                        val frac =
                                            ((clicks - localPrev).toFloat() / (localNext - localPrev))
                                                .coerceIn(0f, 1f)

                                        Spacer(Modifier.height(8.dp))
                                        LinearProgressIndicator(
                                            progress = frac,
                                            color = pink,
                                            trackColor = pink.copy(alpha = 0.25f),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(10.dp)
                                                .clip(RoundedCornerShape(6.dp))
                                        )

                                        Spacer(Modifier.height(4.dp))
                                        Text(
                                            "${clicks - localPrev} / ${localNext - localPrev}",
                                            color = darkText
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}


fun spawnParticlesNearHeart(
    count: Int,
    centerX: Float,
    centerY: Float,
    particles: SnapshotStateList<ParticleState>,
    scope: CoroutineScope
) {
    repeat(count) {
        val id = Random.nextInt()
        // сильнее разлет: +-200..350 пикселей
        val startX = centerX + Random.nextFloat() * 40f - 20f
        val startY = centerY + Random.nextFloat() * 30f - 15f

        val sizeDp = Random.nextInt(12, 28).toFloat()

        val xAnim = Animatable(startX)
        val yAnim = Animatable(startY)
        val aAnim = Animatable(1f)

        val particle = ParticleState(id, xAnim, yAnim, aAnim, sizeDp)
        particles.add(particle)

        scope.launch {
            val duration = 900L + Random.nextLong(0, 400)
            // vx large (+-)
            val vx = (Random.nextDouble(-420.0, 420.0)).toFloat()
            // vy mostly upwards (negative), larger magnitude
            val vy = (Random.nextDouble(-700.0, -220.0)).toFloat()

            val dt = 16L
            var elapsed = 0L
            while (elapsed < duration && particles.contains(particle)) {
                // small physics step
                xAnim.snapTo(xAnim.value + vx * (dt / 1000f))
                yAnim.snapTo(yAnim.value + vy * (dt / 1000f) + 400f * (dt / 1000f) * (dt / 1000f))
                aAnim.snapTo(1f - (elapsed.toFloat() / duration))
                delay(dt)
                elapsed += dt
            }
            aAnim.snapTo(0f)
            particles.remove(particle)
        }
    }
}


fun spawnLightParticles(
    count: Int,
    centerX: Float,
    centerY: Float,
    particles: SnapshotStateList<LightParticle>
) {
    repeat(count) {
        val id = Random.nextInt()

        val endX = centerX + Random.nextFloat() * 1200f - 600f
        val endY = centerY + Random.nextFloat() * -1600f - 200f

        val sizeDp = Random.nextInt(14, 26).toFloat()

        particles.add(
            LightParticle(
                id = id,
                startX = centerX,
                startY = centerY,
                endX = endX,
                endY = endY,
                sizeDp = sizeDp
            )
        )
    }
}


fun lerp(a: Float, b: Float, t: Float): Float {
    return a + (b - a) * t
}

