package com.example.us.ui.screen.special

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.us.ui.component.AnimatedLoveBackground
import com.example.us.ui.screen.calendar.COLOR_PINK
import com.example.us.ui.screen.calendar.COLOR_WHITE
import com.example.us.ui.screen.special.classes.Wish
import com.example.us.ui.screen.special.components.MyWishesList
import com.example.us.ui.screen.special.components.PartnerWishDialog
import com.example.us.ui.screen.special.components.PartnerWishesList
import com.example.us.ui.screen.special.components.WishEditSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishesScreen(viewModel: WishesViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(0) }

    var editWish by remember { mutableStateOf<Wish?>(null) }
    var showPartnerWishDialog by remember { mutableStateOf(false) }
    var selectedPartnerWish by remember { mutableStateOf<Wish?>(null) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val sheetScope = rememberCoroutineScope()
    var sheetVisible by remember { mutableStateOf(false) }

    val closeSheet: () -> Unit = {
        sheetScope.launch { sheetState.hide() }
            .invokeOnCompletion { sheetVisible = false }
    }

    AnimatedLoveBackground(Modifier.fillMaxSize())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .padding(
                top = WindowInsets.statusBars.asPaddingValues()
                    .calculateTopPadding() + 10.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // табы
            TabRow(
                selectedTabIndex = selectedTab,
                backgroundColor = COLOR_PINK,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = COLOR_WHITE,
                        height = 3.dp
                    )
                },
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .height(55.dp)
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Мои", fontSize = 24.sp) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Твои", fontSize = 24.sp) }
                )
            }

            // списки
            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedTab) {
                    0 -> MyWishesList(viewModel, onEdit = { w ->
                        editWish = w
                        sheetVisible = true
                    })

                    1 -> PartnerWishesList(viewModel) { wish ->
                        selectedPartnerWish = wish
                        showPartnerWishDialog = true
                    }
                }
            }
        }

        // кнопка добавления желания
        FloatingActionButton(
            onClick = {
                editWish = null
                sheetVisible = true
            },
            containerColor = Color(0xFFFF6BAB),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = WindowInsets.navigationBars.asPaddingValues()
                        .calculateBottomPadding() + 74.dp
                )
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }

        // модалка
        if (sheetVisible) {
            ModalBottomSheet(
                onDismissRequest = closeSheet,
                sheetState = sheetState,
                dragHandle = { BottomSheetDefaults.DragHandle(color = COLOR_PINK) }
            ) {
                WishEditSheet(
                    wish = editWish,
                    onSave = { w ->
                        if (editWish != null) viewModel.updateWish(w) else viewModel.addMyWish(w)
                        sheetVisible = false
                    },
                    onCancel = { sheetVisible = false },
                    onDelete = { id ->
                        viewModel.removeWish(id)
                        sheetVisible = false
                    }
                )
            }
        }

        if (showPartnerWishDialog && selectedPartnerWish != null) {
            PartnerWishDialog(
                wish = selectedPartnerWish!!,
                onDismiss = { showPartnerWishDialog = false }
            )
        }
    }
}
