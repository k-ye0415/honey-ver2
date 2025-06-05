package com.jin.honey.feature.order.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.CartKey
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.cart.domain.usecase.ChangeQuantityUseCase
import com.jin.honey.feature.cart.domain.usecase.GetCartItemsUseCase
import com.jin.honey.feature.cart.domain.usecase.RemoveCartItemUseCase
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrderViewModel(
    getCartItemsUseCase: GetCartItemsUseCase,
    private val removeCartItemUseCase: RemoveCartItemUseCase,
    private val changeQuantityUseCase: ChangeQuantityUseCase
) : ViewModel() {
    val cartItemState: StateFlow<UiState<List<Cart>>> = getCartItemsUseCase()
        .map { UiState.Success(it) }
        .catch { UiState.Error(it.message.orEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    private val _updateState = MutableSharedFlow<DbState>()
    val updateState = _updateState.asSharedFlow()

    fun removeCartItem(cart: Cart, ingredient: IngredientCart) {
        viewModelScope.launch {
            removeCartItemUseCase(cart, ingredient)
        }
    }

    fun modifyCartQuantity(quantityMap: Map<CartKey, Int>) {
        viewModelScope.launch {
            val result = changeQuantityUseCase(quantityMap)
            if (result.isSuccess) {
                _updateState.emit(DbState.Success)
            } else {
                _updateState.emit(DbState.Error)
            }
        }
    }
}
