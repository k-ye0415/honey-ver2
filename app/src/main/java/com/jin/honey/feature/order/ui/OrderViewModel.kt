package com.jin.honey.feature.order.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.cart.domain.usecase.GetCartItemsUseCase
import com.jin.honey.feature.cart.domain.usecase.RemoveCartItemUseCase
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrderViewModel(
    getCartItemsUseCase: GetCartItemsUseCase,
    private val removeCartItemUseCase: RemoveCartItemUseCase
) : ViewModel() {
    val cartItemState: StateFlow<UiState<List<IngredientCart>>> = getCartItemsUseCase()
        .map { UiState.Success(it) }
        .catch { UiState.Error(it.message.orEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    fun removeCartItem(cart: IngredientCart, ingredient: Ingredient) {
        viewModelScope.launch {
            removeCartItemUseCase(cart, ingredient)
        }
    }
}
