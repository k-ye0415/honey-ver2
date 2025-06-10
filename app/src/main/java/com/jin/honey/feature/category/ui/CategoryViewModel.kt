package com.jin.honey.feature.category.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.usecase.AddIngredientToCartUseCase
import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.usecase.GetAllFoodsUseCase
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val addIngredientToCartUseCase: AddIngredientToCartUseCase,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _allFoodList = MutableStateFlow<UiState<List<Food>>>(UiState.Loading)
    val allFoodList: StateFlow<UiState<List<Food>>> = _allFoodList

    private val _saveCartState = MutableSharedFlow<DbState<Unit>>()
    val saveCartState = _saveCartState.asSharedFlow()

    val saveFavoriteState:StateFlow<List<String>> = preferencesRepository.getFavoriteMenus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun getAllMenus() {
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

    fun toggleFavoriteMenu(menuName: String) {
        viewModelScope.launch {
            preferencesRepository.insertOrUpdateFavoriteMenu(menuName)
        }
    }
}
