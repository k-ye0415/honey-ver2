package com.jin.honey.feature.ingredient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.feature.ui.state.DbState
import com.jin.feature.ui.state.UiState
import com.jin.domain.usecase.AddIngredientToCartUseCase
import com.jin.domain.repositories.PreferencesRepository
import com.jin.domain.usecase.GetIngredientUseCase
import com.jin.domain.usecase.GetReviewUseCase
import com.jin.domain.cart.model.Cart
import com.jin.domain.model.review.Review
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IngredientViewModel(
    private val getIngredientUseCase: GetIngredientUseCase,
    private val addIngredientToCartUseCase: AddIngredientToCartUseCase,
    private val preferencesRepository: PreferencesRepository,
    private val getReviewUseCase: GetReviewUseCase,
) : ViewModel() {
    private val _ingredientState = MutableStateFlow<UiState<com.jin.domain.model.food.IngredientPreview>>(UiState.Loading)
    val ingredientState: StateFlow<UiState<com.jin.domain.model.food.IngredientPreview>> = _ingredientState

    private val _reviewsState = MutableStateFlow<UiState<List<Review>>>(UiState.Loading)
    val reviewsState: StateFlow<UiState<List< Review>>> = _reviewsState

    private val _saveState = MutableSharedFlow<DbState<Unit>>()
    val saveState = _saveState.asSharedFlow()

    val saveFavoriteState: StateFlow<List<String>> = preferencesRepository.flowFavoriteMenus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun fetchMenu(menuName: String) {
        viewModelScope.launch {
            _ingredientState.value = getIngredientUseCase(menuName).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun fetchReview(menuName: String) {
        viewModelScope.launch {
            _reviewsState.value = getReviewUseCase(menuName).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun insertIngredientToCart(cart: Cart) {
        viewModelScope.launch {
            addIngredientToCartUseCase(cart).fold(
                onSuccess = { _saveState.emit(DbState.Success) },
                onFailure = { _saveState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }

    fun toggleFavoriteMenu(menuName: String) {
        viewModelScope.launch {
            preferencesRepository.insertOrUpdateFavoriteMenu(menuName)
        }
    }

    fun updateRecentlyMenu(menuName: String) {
        viewModelScope.launch {
            preferencesRepository.insertRecentlyMenu(menuName)
        }
    }
}
