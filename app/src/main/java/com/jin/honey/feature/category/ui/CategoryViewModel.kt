package com.jin.honey.feature.category.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.usecase.AddIngredientToCartUseCase
import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.usecase.GetAllFoodsUseCase
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val addIngredientToCartUseCase: AddIngredientToCartUseCase
) : ViewModel() {
    private val _allFoodList = MutableStateFlow<UiState<List<Food>>>(UiState.Loading)
    val allFoodList: StateFlow<UiState<List<Food>>> = _allFoodList

    private val _saveState = MutableSharedFlow<DbState>()
    val saveState = _saveState.asSharedFlow()

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
            val result = addIngredientToCartUseCase(cart)
            if (result.isSuccess) {
                _saveState.emit(DbState.Success)
            } else {
                _saveState.emit(DbState.Error)
            }
        }
    }
}
