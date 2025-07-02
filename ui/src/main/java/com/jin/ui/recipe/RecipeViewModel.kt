package com.jin.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.state.UiState
import com.jin.domain.usecase.GetRecipeUseCase
import com.jin.domain.recipe.model.RecipePreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(private val getRecipeUseCase: GetRecipeUseCase) : ViewModel() {
    private val _recipe = MutableStateFlow<UiState<RecipePreview>>(UiState.Loading)
    val recipe: StateFlow<UiState<RecipePreview>> = _recipe

    fun findRecipeByMenuName(menuName: String) {
        viewModelScope.launch {
            _recipe.value = getRecipeUseCase(menuName).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
