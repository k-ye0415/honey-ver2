package com.jin.ui.recipe.myrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.domain.recipe.model.Recipe
import com.jin.domain.usecase.SaveMyRecipeUseCase
import kotlinx.coroutines.launch

class MyRecipeViewModel(private val saveMyRecipeUseCase: SaveMyRecipeUseCase) : ViewModel() {
    fun saveMyRecipe(recipe: Recipe) {
        viewModelScope.launch {
            saveMyRecipeUseCase(recipe)
        }
    }
}
