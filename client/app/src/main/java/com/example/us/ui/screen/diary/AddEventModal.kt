package com.example.us.ui.screen.diary

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.us.ui.screen.calendar.COLOR_PINK
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventModal(
    initialEvent: DiaryEvent? = null,
    onSave: (DiaryEvent, Uri?) -> Unit,
    onDismiss: () -> Unit,
    onDelete: ((DiaryEvent) -> Unit)? = null
) {
    val ctx = LocalContext.current

    var title by remember { mutableStateOf(initialEvent?.title ?: "") }
    var text by remember { mutableStateOf(initialEvent?.description ?: "") }
    var type by remember { mutableStateOf(initialEvent?.type ?: EventType.ROMANTIC) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var pickedDateMillis by remember {
        mutableLongStateOf(
            initialEvent?.date ?: System.currentTimeMillis()
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                ctx.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) { /* иногда уже есть разрешение */
            }

            selectedImageUri = uri
        }
    }


    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(18.dp)) {

            Text(
                if (initialEvent == null) "Новое событие" else "Редактировать событие",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            // Фото-зона
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(COLOR_PINK.copy(alpha = 0.12f))
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                val previewImageUrl: String? = selectedImageUri?.toString() ?: initialEvent?.imageLargeUrl

                if (previewImageUrl == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            tint = COLOR_PINK,
                            modifier = Modifier.size(36.dp)
                        )
                        Text("Загрузить фото", color = COLOR_PINK)
                    }
                } else {
                    AsyncImage(
                        model = previewImageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Дата
            Calendar.getInstance().apply { timeInMillis = pickedDateMillis }
            val fmt = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }

            Text("Дата события", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))

            Button(
                onClick = {
                    val current = Calendar.getInstance().apply { timeInMillis = pickedDateMillis }
                    DatePickerDialog(
                        ctx,
                        { _, y, m, d ->
                            Calendar.getInstance().apply {
                                set(y, m, d, 12, 0, 0)
                                pickedDateMillis = timeInMillis
                            }
                        },
                        current.get(Calendar.YEAR),
                        current.get(Calendar.MONTH),
                        current.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = COLOR_PINK.copy(alpha = 0.12f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(fmt.format(Date(pickedDateMillis)), color = COLOR_PINK)
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Описание") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(Modifier.height(12.dp))

            Text("Эмоция", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(EventType.values().toList()) { t ->
                    AssistChip(
                        onClick = { type = t },
                        label = {
                            Text(
                                eventTypeRussian(t),
                                color = if (t == type) Color.White else eventColor(t)
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (t == type) eventColor(
                                t
                            ) else eventColor(t).copy(alpha = 0.12f)
                        ),
                        border = null
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            Button(
                onClick = {
                    val id = initialEvent?.id ?: (System.currentTimeMillis() and 0xffffffff).toInt()
                    val ev = DiaryEvent(
                        id = id,
                        title = title.ifBlank { "Событие" },
                        description = text,
                        date = pickedDateMillis,
                        imageSmallUrl = initialEvent?.imageSmallUrl,
                        imageLargeUrl = initialEvent?.imageLargeUrl,
                        type = type
                    )
                    onSave(ev, selectedImageUri)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = COLOR_PINK)
            ) {
                Text(if (initialEvent == null) "Добавить" else "Сохранить", color = Color.White)
            }

            if (initialEvent != null && onDelete != null) {
                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        onDelete(initialEvent)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B6B))
                ) {
                    Text("Удалить", color = Color.White)
                }
            }
        }
    }
}
