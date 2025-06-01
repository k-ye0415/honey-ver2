package com.jin.honey.feature.ingredient.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jin.honey.feature.cart.IngredientCart
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.ingredient.ui.content.IngredientAddedCart
import com.jin.honey.feature.ingredient.ui.content.IngredientBody
import com.jin.honey.feature.ingredient.ui.content.IngredientHeader
import com.jin.honey.feature.ingredient.ui.content.IngredientTitle
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.feature.ui.systemBottomBarHeightDp
import com.jin.honey.feature.ui.systemTopStatusHeightDp

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
    val eachIngredientStates = remember {
        mutableStateMapOf<String, IngredientCart>().apply {
            for (ingredient in menu.ingredient) {
                put(
                    ingredient.name,
                    IngredientCart(
                        isSelected = false,
                        menuName = menu.name,
                        ingredientName = ingredient.name,
                        quantity = 1,
                        totalPrice = ingredient.unitPrice
                    )
                )
            }
        }
    }
    // 전체 선택 상태는 derivedStateOf로 "계산"
    val isAllIngredientChecked by remember {
        derivedStateOf { eachIngredientStates.values.all { it.isSelected } }
    }
    val showAddedCart by remember {
        derivedStateOf { isAllIngredientChecked || eachIngredientStates.values.any { it.isSelected } }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = systemBottomBarHeightDp())
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (showAddedCart) 100.dp else 0.dp)
        ) {
            item {
                IngredientHeader(
                    imageUrl = menu.imageUrl,
                    statusTopHeightDp = systemTopStatusHeightDp(),
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
                    isAllIngredientChecked = isAllIngredientChecked,
                    onAllCheckedChange = { newCheck, totalQuantity, totalPrice ->
                        for (ingredientName in eachIngredientStates.keys) {
                            eachIngredientStates[ingredientName] = IngredientCart(
                                isSelected = newCheck,
                                menuName = menu.name,
                                ingredientName = ingredientName,
                                quantity = totalQuantity,
                                totalPrice = totalPrice
                            )
                        }
                    },
                    ingredientCheckMap = eachIngredientStates,
                    onCheckChanged = { name, newCheck, totalQuantity, totalPrice ->
                        eachIngredientStates[name] = IngredientCart(
                            isSelected = newCheck,
                            menuName = menu.name,
                            ingredientName = name,
                            quantity = totalQuantity,
                            totalPrice = totalPrice
                        )
                    },
                )
            }
        }
        AnimatedVisibility(
            visible = showAddedCart,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            val ingredientCartMap = mutableMapOf<String, IngredientCart>()
            for ((key, value) in eachIngredientStates) {
                if (value.isSelected) {
                    ingredientCartMap[key] = value
                }
            }
            IngredientAddedCart(
                modifier = Modifier.fillMaxWidth(),
                ingredientCartMap = ingredientCartMap,
                onAddedCart = {}
            )
        }
    }
}
