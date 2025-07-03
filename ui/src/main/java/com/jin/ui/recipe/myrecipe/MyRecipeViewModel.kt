package com.jin.ui.recipe.myrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.domain.recipe.model.Recipe
import com.jin.domain.usecase.SaveMyRecipeUseCase
import com.jin.state.DbState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MyRecipeViewModel(private val saveMyRecipeUseCase: SaveMyRecipeUseCase) : ViewModel() {
    private val _insertState = MutableSharedFlow<DbState<Unit>>()
    val insertState = _insertState.asSharedFlow()

    fun saveMyRecipe(recipe: Recipe) {
        viewModelScope.launch {
            saveMyRecipeUseCase(recipe).fold(
                onSuccess = { _insertState.emit(DbState.Success) },
                onFailure = { _insertState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }
}
