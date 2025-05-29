package com.jin.honey.feature.category.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.food.domain.usecase.GetAllMenusUseCase
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val getAllMenusUseCase: GetAllMenusUseCase) : ViewModel() {
    private val _allCategoryList = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val allCategoryList: StateFlow<UiState<List<Category>>> = _allCategoryList

    fun getAllMenus() {
        viewModelScope.launch {
            _allCategoryList.value = getAllMenusUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
