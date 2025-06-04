package com.jin.honey.feature.order.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.cart.domain.usecase.GetCartItemsUseCase
import com.jin.honey.feature.cart.domain.usecase.RemoveCartItemUseCase
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeCartItemUseCase: RemoveCartItemUseCase
) : ViewModel() {
    private val _cartItemState = MutableStateFlow<UiState<List<IngredientCart>>>(UiState.Loading)
    val cartItemState: StateFlow<UiState<List<IngredientCart>>> = _cartItemState

    fun findCartItems() {
        viewModelScope.launch {
            _cartItemState.value = getCartItemsUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun removeCartItem(cart: IngredientCart, ingredient: Ingredient) {
        viewModelScope.launch {
            removeCartItemUseCase(cart, ingredient)
        }
    }
}
