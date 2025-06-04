package com.jin.honey.feature.recipe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.food.domain.model.Recipe
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _recipe = MutableStateFlow<UiState<Recipe>>(UiState.Loading)
    val recipe: StateFlow<UiState<Recipe>> = _recipe

    fun findRecipeByMenuName(menuName: String) {
        viewModelScope.launch {

        }
    }
}
