package com.example.us.ui.screen.auth

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.us.ui.component.AnimatedLoveBackground
import com.example.us.ui.screen.calendar.COLOR_PINK
import com.example.us.ui.screen.calendar.COLOR_WHITE
import com.example.us.ui.theme.COLOR_PINK_BG

@Composable
fun RegisterScreen(
    context: Context = LocalContext.current
) {
    val viewModel: RegisterViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RegisterViewModel(context) as T
            }
        }
    )

    var name by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFFFB7D4)
            )
    ) {
        AnimatedLoveBackground(Modifier.fillMaxSize())

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Имя") },
                placeholder = { Text("Как тебя называть?") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = COLOR_WHITE.copy(alpha = 0.3f),
                    focusedContainerColor = COLOR_WHITE.copy(alpha = 0.3f),
                    unfocusedBorderColor = COLOR_PINK,
                    focusedBorderColor = COLOR_PINK,
                    focusedLabelColor = COLOR_PINK,
                    focusedPlaceholderColor = Color.Black.copy(alpha = 0.5f)
                )
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.register(name) },
                enabled = name.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = COLOR_PINK,
                    contentColor = Color.White,
                    disabledContainerColor = COLOR_PINK.copy(alpha = 0.5f),
                    disabledContentColor = Color.White.copy(alpha = 0.5f)
                )
            ) {
                Text("Зарегистрироваться")
            }
        }
    }
}

