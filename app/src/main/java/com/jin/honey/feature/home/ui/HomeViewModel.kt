package com.jin.honey.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.food.domain.usecase.GetCategoryNamesUseCase
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCategoryNamesUseCase: GetCategoryNamesUseCase
) : ViewModel() {
    private val _categoryNameList = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val categoryNameList: StateFlow<UiState<List<String>>> = _categoryNameList

    fun launchCategoryTypeList() {
        viewModelScope.launch {
            _categoryNameList.value = getCategoryNamesUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
