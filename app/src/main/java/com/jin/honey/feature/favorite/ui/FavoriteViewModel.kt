package com.jin.honey.feature.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.favorite.domain.GetFavoriteMenuUseCase
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriteMenuUseCase: GetFavoriteMenuUseCase,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _favoriteMenuState = MutableStateFlow<UiState<List<MenuPreview>>>(UiState.Loading)
    val favoriteMenuState: StateFlow<UiState<List<MenuPreview>>> = _favoriteMenuState

    init {
        fetchFavoriteMenus()
    }

    private fun fetchFavoriteMenus() {
        viewModelScope.launch {
            getFavoriteMenuUseCase().collect { result ->
                _favoriteMenuState.value = result.fold(
                    onSuccess = { UiState.Success(it) },
                    onFailure = { UiState.Error(it.message.orEmpty()) }
                )
            }
        }
    }

    fun toggleFavoriteMenu(menuName: String) {
        viewModelScope.launch {
            preferencesRepository.insertOrUpdateFavoriteMenu(menuName)
        }
    }
}
