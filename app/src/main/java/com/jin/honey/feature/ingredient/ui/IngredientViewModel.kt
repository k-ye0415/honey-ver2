package com.jin.honey.feature.ingredient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.food.domain.usecase.GetMenuIngredientUseCase
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IngredientViewModel(private val getMenuIngredientUseCase: GetMenuIngredientUseCase) : ViewModel() {
    private val _menu = MutableStateFlow<UiState<Menu>>(UiState.Loading)
    val menu: StateFlow<UiState<Menu>> = _menu

    fun fetchMenu(menuName: String) {
        viewModelScope.launch {
            _menu.value = getMenuIngredientUseCase(menuName).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

}
