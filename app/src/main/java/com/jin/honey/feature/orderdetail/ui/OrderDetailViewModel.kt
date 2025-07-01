package com.jin.honey.feature.orderdetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.domain.address.model.Address
import com.jin.feature.ui.state.DbState
import com.jin.feature.ui.state.SearchState
import com.jin.feature.ui.state.UiState
import com.jin.domain.usecase.ChangeCurrentAddressUseCase
import com.jin.domain.usecase.GetAddressesUseCase
import com.jin.domain.usecase.SearchAddressUseCase
import com.jin.domain.usecase.ChangeQuantityOfCartUseCase
import com.jin.domain.usecase.GetCartItemsUseCase
import com.jin.domain.usecase.RemoveIngredientInCartItemUseCase
import com.jin.domain.usecase.RemoveMenuInCartUseCase
import com.jin.domain.model.order.Order
import com.jin.domain.usecase.PayAndOrderUseCase
import com.jin.domain.address.model.SearchAddress
import com.jin.domain.model.cart.Cart
import com.jin.model2.cart.CartKey
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
    getAddressesUseCase: GetAddressesUseCase,
    private val searchAddressUseCase: SearchAddressUseCase,
    getCartItemsUseCase: GetCartItemsUseCase,
    private val removeIngredientInCartItemUseCase: RemoveIngredientInCartItemUseCase,
    private val changeQuantityOfCartUseCase: ChangeQuantityOfCartUseCase,
    private val removeMenuInCartUseCase: RemoveMenuInCartUseCase,
    private val payAndOrderUseCase: PayAndOrderUseCase,
    private val changeCurrentAddressUseCase: ChangeCurrentAddressUseCase
) : ViewModel() {
    val cartItemState: StateFlow<UiState<List<Cart>>> = getCartItemsUseCase()
        .map { UiState.Success(it) }
        .catch { UiState.Error(it.message.orEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    val addressesState: StateFlow<UiState<List<Address>>> = getAddressesUseCase()
        .map { UiState.Success(it) }
        .catch { UiState.Error(it.message.orEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    private val _searchAddressSearchState = MutableStateFlow<SearchState<List<SearchAddress>>>(SearchState.Idle)
    val searchAddressSearchState: StateFlow<SearchState<List<SearchAddress>>> = _searchAddressSearchState

    private val _updateState = MutableSharedFlow<DbState<Unit>>()
    val updateState = _updateState.asSharedFlow()

    private val _insertState = MutableSharedFlow<DbState<Unit>>()
    val insertState = _insertState.asSharedFlow()

    private val _addressChangeState = MutableSharedFlow<DbState<Unit>>()
    val addressChangeState = _addressChangeState.asSharedFlow()

    fun searchAddressByKeyword(keyword: String) {
        if (keyword.isBlank()) {
            _searchAddressSearchState.value = SearchState.Idle
            return
        }
        viewModelScope.launch {
            _searchAddressSearchState.value = SearchState.Loading
            _searchAddressSearchState.value = searchAddressUseCase(keyword).fold(
                onSuccess = { SearchState.Success(it) },
                onFailure = { SearchState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun removeIngredientInCartItem(cart:  Cart, ingredientName: String) {
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

    fun removeMenuInCartItem(cartItem:  Cart) {
        viewModelScope.launch {
            removeMenuInCartUseCase(cartItem)
        }
    }

    fun saveAfterPayment(order: Order) {
        viewModelScope.launch {
            payAndOrderUseCase(order).fold(
                onSuccess = { _insertState.emit(DbState.Success) },
                onFailure = { _insertState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }

    fun changedAddress(address: Address) {
        viewModelScope.launch {
            changeCurrentAddressUseCase(address).fold(
                onSuccess = { _addressChangeState.emit(DbState.Success) },
                onFailure = { _addressChangeState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }
}
