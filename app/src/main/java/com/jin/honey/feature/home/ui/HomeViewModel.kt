package com.jin.honey.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.usecase.GetAllMenusUseCase
import com.jin.honey.feature.food.domain.usecase.GetCategoryUseCase
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllMenusUseCase: GetAllMenusUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {
    private val _allCategoryList = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val allCategoryList: StateFlow<UiState<List<Category>>> = _allCategoryList

    private val _categoryList = MutableStateFlow<UiState<List<CategoryType>>>(UiState.Loading)
    val categoryList: StateFlow<UiState<List<CategoryType>>> = _categoryList

    fun getAllFoodList() {
        viewModelScope.launch {

            _allCategoryList.value = getAllMenusUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
            _categoryList.value = getCategoryUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
