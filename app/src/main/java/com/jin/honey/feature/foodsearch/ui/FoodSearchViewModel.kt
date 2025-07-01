package com.jin.honey.feature.foodsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.feature.ui.state.SearchState
import com.jin.domain.repositories.PreferencesRepository
import com.jin.domain.usecase.SearchMenusUseCase
import com.jin.domain.food.model.MenuPreview
import com.jin.domain.search.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FoodSearchViewModel(
    private val repository: SearchRepository,
    private val searchMenusUseCase: SearchMenusUseCase
) : ViewModel() {
    private val _menuSearchState = MutableStateFlow<SearchState<List<MenuPreview>>>(SearchState.Idle)
    val menuSearchState: StateFlow<SearchState<List<MenuPreview>>> = _menuSearchState

    val searchKeywordState: StateFlow<List<String>> = repository.flowSearchKeywords()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

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

    fun saveSearchKeyword(menuName: String) {
        viewModelScope.launch {
            repository.saveSearchKeyword(menuName)
        }
    }

    fun deleteSearchKeyword(menuName: String) {
        viewModelScope.launch {
            repository.deleteSearchKeyword(menuName)
        }
    }

    fun clearSearchKeyword(){
        viewModelScope.launch {
            repository.clearSearchKeyword()
        }
    }
}
