package com.jin.honey.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.food.domain.usecase.GetAllFoodUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: GetAllFoodUseCase) : ViewModel() {
    private val _allFoodList = MutableStateFlow<List<Category>>(emptyList())
    val allFoodList: StateFlow<List<Category>> = _allFoodList

    fun getAllFoodList() {
        viewModelScope.launch {
            _allFoodList.value = useCase()
        }
    }
}
