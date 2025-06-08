package com.jin.honey.feature.foodsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.food.domain.usecase.SearchMenusUseCase
import com.jin.honey.feature.ui.state.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodSearchViewModel(private val searchMenusUseCase: SearchMenusUseCase) : ViewModel() {
    private val _menuSearchState = MutableStateFlow<SearchState<List<MenuPreview>>>(SearchState.Idle)
    val menuSearchState: StateFlow<SearchState<List<MenuPreview>>> = _menuSearchState

    fun searchMenuByKeyword(keyword: String) {
        if (keyword.isBlank()) {
            _menuSearchState.value = SearchState.Idle
            return
        }
        viewModelScope.launch {
            _menuSearchState.value = SearchState.Loading
            _menuSearchState.value = searchMenusUseCase(keyword).fold(
                onSuccess = { SearchState.Success(it) },
                onFailure = { SearchState.Error(it.message.orEmpty()) }
            )
        }
    }
}
