package com.jin.honey.feature.category.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.address.domain.model.SearchAddress
import com.jin.honey.feature.address.domain.model.Address
import com.jin.honey.feature.address.domain.usecase.GetAddressesUseCase
import com.jin.honey.feature.address.domain.usecase.SearchAddressUseCase
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.usecase.AddIngredientToCartUseCase
import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.usecase.GetAllFoodsUseCase
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val searchAddressUseCase: SearchAddressUseCase,
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val addIngredientToCartUseCase: AddIngredientToCartUseCase,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _AddressesState = MutableStateFlow<UiState<List<Address>>>(UiState.Loading)
    val addressesState: StateFlow<UiState<List<Address>>> = _AddressesState

    private val _Search_addressSearchState = MutableStateFlow<SearchState<List<SearchAddress>>>(SearchState.Idle)
    val searchAddressSearchState: StateFlow<SearchState<List<SearchAddress>>> = _Search_addressSearchState

    private val _allFoodList = MutableStateFlow<UiState<List<Food>>>(UiState.Loading)
    val allFoodList: StateFlow<UiState<List<Food>>> = _allFoodList

    private val _saveCartState = MutableSharedFlow<DbState<Unit>>()
    val saveCartState = _saveCartState.asSharedFlow()

    val saveFavoriteState: StateFlow<List<String>> = preferencesRepository.flowFavoriteMenus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    init {
        getAllMenus()
        checkIfAddressesIsEmpty()
    }

    private fun checkIfAddressesIsEmpty() {
        viewModelScope.launch {
            _AddressesState.value = getAddressesUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    private fun getAllMenus() {
        viewModelScope.launch {
            _allFoodList.value = getAllFoodsUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun insertIngredientToCart(cart: Cart) {
        viewModelScope.launch {
            addIngredientToCartUseCase(cart).fold(
                onSuccess = { _saveCartState.emit(DbState.Success) },
                onFailure = { _saveCartState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }

    fun searchAddressByKeyword(keyword: String) {
        if (keyword.isBlank()) {
            _Search_addressSearchState.value = SearchState.Idle
            return
        }
        viewModelScope.launch {
            _Search_addressSearchState.value = SearchState.Loading
            _Search_addressSearchState.value = searchAddressUseCase(keyword).fold(
                onSuccess = { SearchState.Success(it) },
                onFailure = { SearchState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun toggleFavoriteMenu(menuName: String) {
        viewModelScope.launch {
            preferencesRepository.insertOrUpdateFavoriteMenu(menuName)
        }
    }
}
