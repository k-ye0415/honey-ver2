package com.jin.honey.feature.ingredient.ui

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.ingredient.model.IngredientPreview
import com.jin.honey.feature.ingredient.ui.content.IngredientAddedCart
import com.jin.honey.feature.ingredient.ui.content.IngredientBody
import com.jin.honey.feature.ingredient.ui.content.IngredientHeader
import com.jin.honey.feature.ingredient.ui.content.IngredientTitle
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.feature.ui.systemBottomBarHeightDp
import com.jin.honey.feature.ui.systemTopStatusHeightDp
import java.time.Instant

@Composable
fun IngredientScreen(
    viewModel: IngredientViewModel,
    menuName: String,
    onNavigateToCategory: () -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
    onNavigateToReview: (menuName: String) -> Unit,
) {
    val context = LocalContext.current
    val ingredientState by viewModel.ingredientState.collectAsState()
    var ingredientSelections by remember { mutableStateOf<Map<String, Boolean>>(emptyMap()) }
    val favoriteState by viewModel.saveFavoriteState.collectAsState()
    val isFavorite = favoriteState.contains(menuName)

    LaunchedEffect(Unit) {
        viewModel.saveState.collect {
            when (it) {
                is DbState.Success -> {
                    Toast.makeText(context, context.getString(R.string.cart_toast_save_success), Toast.LENGTH_SHORT)
                        .show()
                    ingredientSelections = ingredientSelections.mapValues { false }
                }

                is DbState.Error -> Toast.makeText(
                    context,
                    context.getString(R.string.cart_toast_save_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchMenu(menuName)
    }

    when (val state = ingredientState) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> {
            if (ingredientSelections.isEmpty()) {
                ingredientSelections = state.data.ingredients.associate { it.name to false }
            }
            IngredientSuccess(
                menu = state.data,
                isFavorite = isFavorite,
                ingredientSelections = ingredientSelections,
                onChangedRecentlyMenu = { viewModel.updateRecentlyMenu(menuName) },
                onAllCheckedChange = { newCheck ->
                    ingredientSelections = state.data.ingredients.associate { it.name to true }
                },
                onCheckChanged = { name, newCheck ->
                    ingredientSelections = ingredientSelections.toMutableMap().apply {
                        this[name] = newCheck
                    }
                },
                onInsertCart = { viewModel.insertIngredientToCart(it) },
                onClickFavorite = { viewModel.toggleFavoriteMenu(it) },
                onNavigateToCategory = onNavigateToCategory,
                onNavigateToRecipe = onNavigateToRecipe,
                onNavigateToReview = onNavigateToReview
            )
        }

        is UiState.Error -> CircularProgressIndicator()
    }
}

@Composable
private fun IngredientSuccess(
    menu: IngredientPreview,
    isFavorite: Boolean,
    ingredientSelections: Map<String, Boolean>,
    onChangedRecentlyMenu: () -> Unit,
    onAllCheckedChange: (newCheck: Boolean) -> Unit,
    onCheckChanged: (name: String, newCheck: Boolean) -> Unit,
    onInsertCart: (cart: Cart) -> Unit,
    onClickFavorite: (menuName: String) -> Unit,
    onNavigateToCategory: () -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
    onNavigateToReview: (menuName: String) -> Unit,
) {
    // 전체 선택 상태는 derivedStateOf로 "계산"
    val allIngredientsSelected by remember(ingredientSelections) {
        derivedStateOf { ingredientSelections.values.all { it } }
    }
    val shouldShowCart by remember(ingredientSelections) {
        derivedStateOf { allIngredientsSelected || ingredientSelections.values.any { it } }
    }

    LaunchedEffect(Unit) {
        onChangedRecentlyMenu()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = systemBottomBarHeightDp())
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (shouldShowCart) 100.dp else 0.dp)
        ) {
            item {
                IngredientHeader(
                    isFavorite = isFavorite,
                    imageUrl = menu.imageUrl,
                    statusTopHeightDp = systemTopStatusHeightDp(),
                    onNavigateToCategory = onNavigateToCategory,
                    onClickShare = {},
                    onClickFavorite = { onClickFavorite(menu.menuName) })
            }
            item {
                IngredientTitle(
                    menuName = menu.menuName,
                    onClickShowReview = { onNavigateToReview(menu.menuName) },
                    onNavigateToRecipe = { onNavigateToRecipe(menu.menuName) },
                    onClickMyRecipe = {}
                )
            }
            item {
                IngredientBody(
                    menuName = menu.menuName,
                    ingredientList = menu.ingredients,
                    allIngredientsSelected = allIngredientsSelected,
                    ingredientSelections = ingredientSelections,
                    onAllCheckedChange = onAllCheckedChange,
                    onCheckChanged = onCheckChanged,
                )
            }
        }
        AnimatedVisibility(
            visible = shouldShowCart,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            val ingredientList = mutableListOf<IngredientCart>()
            for ((key, value) in ingredientSelections) {
                if (value) {
                    val ingredient = menu.ingredients.find { it.name == key } ?: return@AnimatedVisibility
                    ingredientList.add(
                        IngredientCart(
                            name = ingredient.name,
                            cartQuantity = 1,
                            quantity = ingredient.quantity,
                            unitPrice = ingredient.unitPrice
                        )
                    )
                }
            }
            val cart = Cart(
                id = null,
                addedCartInstant = Instant.now(),
                menuName = menu.menuName,
                menuImageUrl = menu.imageUrl,
                ingredients = ingredientList,
                isOrdered = false
            )
            IngredientAddedCart(
                modifier = Modifier.fillMaxWidth(),
                cart = cart,
                onAddedCart = { onInsertCart(cart) }
            )
        }
    }
}
