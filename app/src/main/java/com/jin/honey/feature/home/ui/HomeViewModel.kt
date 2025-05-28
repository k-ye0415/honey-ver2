package com.jin.honey.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.food.domain.usecase.GetAllFoodUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: GetAllFoodUseCase) : ViewModel() {
    private val _allFoodList = MutableStateFlow<List<Menu>>(emptyList())
    val allFoodList: StateFlow<List<Menu>> = _allFoodList

    fun getAllFoodList() {
        viewModelScope.launch {
            _allFoodList.value = useCase()
        }
    }
}
