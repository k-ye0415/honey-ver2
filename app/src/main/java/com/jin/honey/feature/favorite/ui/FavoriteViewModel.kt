package com.jin.honey.feature.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.favorite.domain.GetFavoriteMenuUseCase
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val getFavoriteMenuUseCase: GetFavoriteMenuUseCase) : ViewModel() {
    private val _favoriteMenuState = MutableStateFlow<UiState<List<MenuPreview>>>(UiState.Loading)
    val favoriteMenuState: StateFlow<UiState<List<MenuPreview>>> = _favoriteMenuState

    init {
        fetchFavoriteMenus()
    }

    private fun fetchFavoriteMenus() {
        viewModelScope.launch {
            _favoriteMenuState.value = getFavoriteMenuUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
