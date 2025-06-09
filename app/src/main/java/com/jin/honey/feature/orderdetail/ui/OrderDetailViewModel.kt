package com.jin.honey.feature.orderdetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.CartKey
import com.jin.honey.feature.cart.domain.usecase.ChangeQuantityOfCartUseCase
import com.jin.honey.feature.cart.domain.usecase.GetCartItemsUseCase
import com.jin.honey.feature.cart.domain.usecase.RemoveIngredientInCartItemUseCase
import com.jin.honey.feature.cart.domain.usecase.RemoveMenuInCartUseCase
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.UserAddress
import com.jin.honey.feature.district.domain.usecase.GetLatestAddressUseCase
import com.jin.honey.feature.district.domain.usecase.SearchAddressUseCase
import com.jin.honey.feature.payment.domain.PayAndOrderUseCase
import com.jin.honey.feature.payment.domain.Payment
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrderDetailViewModel(
    private val getLatestAddressUseCase: GetLatestAddressUseCase,
    private val searchAddressUseCase: SearchAddressUseCase,
    getCartItemsUseCase: GetCartItemsUseCase,
    private val removeIngredientInCartItemUseCase: RemoveIngredientInCartItemUseCase,
    private val changeQuantityOfCartUseCase: ChangeQuantityOfCartUseCase,
    private val removeMenuInCartUseCase: RemoveMenuInCartUseCase,
    private val payAndOrderUseCase: PayAndOrderUseCase
) : ViewModel() {
    val cartItemState: StateFlow<UiState<List<Cart>>> = getCartItemsUseCase()
        .map { UiState.Success(it) }
        .catch { UiState.Error(it.message.orEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    private val _latestAddressState = MutableStateFlow<UiState<UserAddress>>(UiState.Loading)
    val latestAddressState: StateFlow<UiState<UserAddress>> = _latestAddressState

    private val _addressSearchState = MutableStateFlow<SearchState<List<Address>>>(SearchState.Idle)
    val addressSearchState: StateFlow<SearchState<List<Address>>> = _addressSearchState

    private val _updateState = MutableSharedFlow<DbState<Unit>>()
    val updateState = _updateState.asSharedFlow()

    init {
        requestLatestAddress()
    }

    private fun requestLatestAddress() {
        viewModelScope.launch {
            _latestAddressState.value = getLatestAddressUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun searchAddressByKeyword(keyword: String) {
        if (keyword.isBlank()) {
            _addressSearchState.value = SearchState.Idle
            return
        }
        viewModelScope.launch {
            _addressSearchState.value = SearchState.Loading
            _addressSearchState.value = searchAddressUseCase(keyword).fold(
                onSuccess = { SearchState.Success(it) },
                onFailure = { SearchState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun removeIngredientInCartItem(cart: Cart, ingredientName: String) {
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

    fun removeMenuInCartItem(cartItem: Cart) {
        viewModelScope.launch {
            removeMenuInCartUseCase(cartItem)
        }
    }

    fun payAndOrder(payment: Payment) {
        viewModelScope.launch {
            payAndOrderUseCase(payment)
        }
    }
}
