package com.jin.honey.feature.order.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.CartKey
import com.jin.honey.feature.cart.domain.usecase.ChangeQuantityOfCartUseCase
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
    private val changeQuantityOfCartUseCase: ChangeQuantityOfCartUseCase
) : ViewModel() {
    val cartItemState: StateFlow<UiState<List<Cart>>> = getCartItemsUseCase()
        .map { UiState.Success(it) }
        .catch { UiState.Error(it.message.orEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    private val _updateState = MutableSharedFlow<DbState<Unit>>()
    val updateState = _updateState.asSharedFlow()

    fun removeCartItem(cart: Cart, ingredientName: String) {
        viewModelScope.launch {
            removeCartItemUseCase(cart, ingredientName)
        }
    }

    fun modifyCartQuantity(quantityMap: Map<CartKey, Int>) {
        viewModelScope.launch {
            changeQuantityOfCartUseCase(quantityMap).fold(
                onSuccess = { _updateState.emit(DbState.Success) },
                onFailure = { _updateState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }
}
