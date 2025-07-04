package com.jin.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.state.UiState
import com.jin.domain.favorite.FavoriteRepository
import com.jin.domain.usecase.GetFavoriteMenuUseCase
import com.jin.domain.usecase.GetRecentlyMenuUseCase
import com.jin.domain.favorite.FavoritePreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriteMenuUseCase: GetFavoriteMenuUseCase,
    private val getRecentlyMenuUseCase: GetRecentlyMenuUseCase,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _favoriteMenuState = MutableStateFlow<UiState<List<FavoritePreview>>>(UiState.Loading)
    val favoriteMenuState: StateFlow<UiState<List<FavoritePreview>>> = _favoriteMenuState

    private val _recentlyMenuState = MutableStateFlow<UiState<List<FavoritePreview>>>(UiState.Loading)
    val recentlyMenuState: StateFlow<UiState<List<FavoritePreview>>> = _recentlyMenuState

    init {
        fetchFavoriteMenus()
        fetchRecentlyMenus()
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

    private fun fetchRecentlyMenus() {
        viewModelScope.launch {
            getRecentlyMenuUseCase().collect { result ->
                _recentlyMenuState.value = result.fold(
                    onSuccess = { UiState.Success(it) },
                    onFailure = { UiState.Error(it.message.orEmpty()) }
                )
            }
        }
    }

    fun toggleFavoriteMenu(menuName: String) {
        viewModelScope.launch {
            favoriteRepository.insertOrUpdateFavoriteMenu(menuName)
        }
    }

    fun deleteRecentlyMenu(menuName: String) {
        viewModelScope.launch {
            favoriteRepository.deleteRecentlyMenu(menuName)
        }
    }
}
