package com.jin.honey.feature.category.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.domain.address.model.Address
import com.jin.feature.ui.state.DbState
import com.jin.feature.ui.state.SearchState
import com.jin.feature.ui.state.UiState
import com.jin.domain.usecase.ChangeCurrentAddressUseCase
import com.jin.domain.usecase.GetAddressesUseCase
import com.jin.domain.usecase.SearchAddressUseCase
import com.jin.domain.usecase.AddIngredientToCartUseCase
import com.jin.domain.repositories.PreferencesRepository
import com.jin.domain.usecase.GetAllFoodsUseCase
import com.jin.domain.address.model.SearchAddress
import com.jin.domain.model.cart.Cart
import com.jin.domain.model.food.Food
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoryViewModel(
    getAddressesUseCase: GetAddressesUseCase,
    private val searchAddressUseCase: SearchAddressUseCase,
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val addIngredientToCartUseCase: AddIngredientToCartUseCase,
    private val preferencesRepository: PreferencesRepository,
    private val changeCurrentAddressUseCase: ChangeCurrentAddressUseCase
) : ViewModel() {
    val addressesState: StateFlow<UiState<List<Address>>> = getAddressesUseCase()
        .map { UiState.Success(it) }
        .catch { UiState.Error(it.message.orEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    private val _searchAddressSearchState = MutableStateFlow<SearchState<List<SearchAddress>>>(SearchState.Idle)
    val searchAddressSearchState: StateFlow<SearchState<List<SearchAddress>>> = _searchAddressSearchState

    private val _allFoodList = MutableStateFlow<UiState<List<Food>>>(UiState.Loading)
    val allFoods: StateFlow<UiState<List< Food>>> = _allFoodList

    private val _saveCartState = MutableSharedFlow<DbState<Unit>>()
    val saveCartState = _saveCartState.asSharedFlow()

    val saveFavoriteState: StateFlow<List<String>> = preferencesRepository.flowFavoriteMenus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _addressChangeState = MutableSharedFlow<DbState<Unit>>()
    val addressChangeState = _addressChangeState.asSharedFlow()

    init {
        getAllMenus()
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

    fun toggleFavoriteMenu(menuName: String) {
        viewModelScope.launch {
            preferencesRepository.insertOrUpdateFavoriteMenu(menuName)
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
