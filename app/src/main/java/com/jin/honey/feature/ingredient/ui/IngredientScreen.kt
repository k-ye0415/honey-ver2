package com.jin.honey.feature.ingredient.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.ingredient.ui.content.IngredientBody
import com.jin.honey.feature.ingredient.ui.content.IngredientHeader
import com.jin.honey.feature.ingredient.ui.content.IngredientTitle
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.PointColor

@Composable
fun IngredientScreen(
    viewModel: IngredientViewModel,
    menuName: String,
    onNavigateToCategory: () -> Unit
) {
    val menu by viewModel.menu.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchMenu(menuName)
    }

    when (val state = menu) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> IngredientSuccess(state.data, onNavigateToCategory)
        is UiState.Error -> CircularProgressIndicator()
    }
}

@Composable
private fun IngredientSuccess(menu: Menu, onNavigateToCategory: () -> Unit) {
    val navigationBarHeightDp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.toInt().dp
    val statusTopHeightDp = WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.toInt().dp
    val ingredientCheckedStates = remember {
        mutableStateMapOf<String, Boolean>().apply {
            menu.ingredient.forEach { put(it.name, false) }
        }
    }
    // 전체 선택 상태는 derivedStateOf로 "계산"
    val isAllChecked by remember {
        derivedStateOf { ingredientCheckedStates.values.all { it } }
    }
    val showAddedCart by remember {
        derivedStateOf { isAllChecked || ingredientCheckedStates.values.any { it } }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = navigationBarHeightDp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (showAddedCart) 100.dp else 0.dp)
        ) {
            item {
                IngredientHeader(
                    imageUrl = menu.imageUrl,
                    statusTopHeightDp = statusTopHeightDp,
                    onNavigateToCategory = onNavigateToCategory,
                    onClickShare = {},
                    onClickFavorite = {})
            }
            item {
                IngredientTitle(
                    menuName = menu.name,
                    onClickShowReview = {},
                    onClickShowRecipe = {},
                    onClickMyRecipe = {}
                )
            }
            item {
                IngredientBody(
                    menuName = menu.name,
                    ingredientList = menu.ingredient,
                    isAllChecked = isAllChecked,
                    onAllCheckedChange = { newCheck ->
                        ingredientCheckedStates.keys.forEach { ingredientCheckedStates[it] = newCheck }
                    },
                    checkState = ingredientCheckedStates,
                    onCheckChanged = { name, newCheck ->
                        ingredientCheckedStates[name] = newCheck
                    }
                )
            }
        }
        AnimatedVisibility(
            visible = showAddedCart,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            IngredientAddedCart(Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun IngredientAddedCart(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .border(1.dp, PointColor, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("** 외 2 가 장바구니에 있어요!", modifier = Modifier.padding(top = 14.dp))
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                onClick = {}
            ) {
                Text("***원 배달 주문하기")
            }
        }
    }
}
