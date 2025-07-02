package com.jin.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.state.DbState
import com.jin.state.UiState
import com.jin.domain.usecase.ChangeQuantityOfCartUseCase
import com.jin.domain.usecase.GetCartItemsUseCase
import com.jin.domain.usecase.RemoveIngredientInCartItemUseCase
import com.jin.domain.order.model.Order
import com.jin.domain.usecase.GetOrderHistoriesUseCase
import com.jin.domain.cart.model.Cart
import com.jin.domain.cart.model.CartKey
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrderViewModel(
    getCartItemsUseCase: GetCartItemsUseCase,
    private val removeIngredientInCartItemUseCase: RemoveIngredientInCartItemUseCase,
    private val changeQuantityOfCartUseCase: ChangeQuantityOfCartUseCase,
    private val getOrderHistoriesUseCase: GetOrderHistoriesUseCase
) : ViewModel() {
    val cartItemState: StateFlow<UiState<List<Cart>>> = getCartItemsUseCase()
        .map { UiState.Success(it) }
        .catch { UiState.Error(it.message.orEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    private val _updateState = MutableSharedFlow<DbState<Unit>>()
    val updateState = _updateState.asSharedFlow()

    private val _orderHistoryListState = MutableStateFlow<UiState<List<Order>>>(UiState.Loading)
    val orderHistoryListState: StateFlow<UiState<List<Order>>> = _orderHistoryListState

    init {
        retrieveOrderHistory()
    }

    fun removeCartItem(cart: Cart, ingredientName: String) {
        viewModelScope.launch {
            removeIngredientInCartItemUseCase(cart, ingredientName)
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

    private fun retrieveOrderHistory() {
        viewModelScope.launch {
            _orderHistoryListState.value = getOrderHistoriesUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
