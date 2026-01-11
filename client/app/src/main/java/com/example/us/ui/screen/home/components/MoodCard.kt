package com.example.us.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.us.ui.screen.home.InfoCard
import com.example.us.ui.screen.home.utils.MoodType
import com.example.us.ui.screen.home.utils.moodColor
import com.example.us.ui.screen.home.utils.moodIcon
import com.example.us.ui.screen.home.utils.moodRussian


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodCard(
    mood: MoodType?,
    onMoodSelected: (MoodType) -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }

    val currentMood = mood ?: MoodType.CALM

    InfoCard(title = "Эмоция дня") {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(moodColor(currentMood).copy(alpha = 0.12f))
                .clickable { showSheet = true },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.88f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(moodIcon(currentMood), fontSize = 24.sp)
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    moodRussian(currentMood),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = moodColor(currentMood)
                )
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                itemsIndexed(MoodType.entries) { index, mood ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(moodColor(mood).copy(alpha = 0.12f))
                            .clickable {
                                onMoodSelected(mood)
                                showSheet = false
                            }
                            .padding(vertical = 12.dp, horizontal = 10.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.88f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = moodIcon(mood), fontSize = 18.sp)
                            }
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = moodRussian(mood),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = moodColor(mood)
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                }
            }
        }
    }
}
