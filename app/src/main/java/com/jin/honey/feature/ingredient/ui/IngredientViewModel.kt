package com.jin.honey.feature.ingredient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.usecase.AddIngredientToCartUseCase
import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.food.domain.usecase.GetIngredientUseCase
import com.jin.honey.feature.ingredient.model.IngredientPreview
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class IngredientViewModel(
    private val getIngredientUseCase: GetIngredientUseCase,
    private val addIngredientToCartUseCase: AddIngredientToCartUseCase,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _ingredientState = MutableStateFlow<UiState<IngredientPreview>>(UiState.Loading)
    val ingredientState: StateFlow<UiState<IngredientPreview>> = _ingredientState

    private val _saveState = MutableSharedFlow<DbState<Unit>>()
    val saveState = _saveState.asSharedFlow()

    fun fetchMenu(menuName: String) {
        viewModelScope.launch {
            _ingredientState.value = getIngredientUseCase(menuName).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun insertIngredientToCart(cart: Cart) {
        viewModelScope.launch {
            addIngredientToCartUseCase(cart).fold(
                onSuccess = { _saveState.emit(DbState.Success) },
                onFailure = { _saveState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }

    fun saveFavoriteMenu(menuName: String) {
        viewModelScope.launch {
            preferencesRepository.saveFavoriteMenu(menuName)
        }
    }
}
