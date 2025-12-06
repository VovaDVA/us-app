package com.example.us.ui.screen.games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RanksDialog(currentClicks: Int, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            TextButton(onClick = onClose) { Text("Закрыть") }
        },
        title = { Text("Ранги") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                HeartRanks.names.forEachIndexed { idx, name ->
                    val threshold = HeartRanks.thresholds.getOrElse(idx) { 0 }
                    val achieved = currentClicks >= threshold
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(name, color = if (achieved) Color(0xFFFF6BAB) else Color.White.copy(alpha = 0.8f))
                        Text(
                            (if (achieved) "достигнуто" else "${threshold - currentClicks} до цели"),
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    )
}
