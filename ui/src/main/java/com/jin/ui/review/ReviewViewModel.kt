package com.jin.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.state.UiState
import com.jin.domain.usecase.GetReviewWithIngredientUseCase
import com.jin.domain.review.ReviewPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val getReviewWithIngredientUseCase: GetReviewWithIngredientUseCase,
) : ViewModel() {
    private val _reviewsState = MutableStateFlow<UiState<List<ReviewPreview>>>(UiState.Loading)
    val reviewsState: StateFlow<UiState<List<ReviewPreview>>> = _reviewsState

    fun fetchReview(menuName: String) {
        viewModelScope.launch {
            _reviewsState.value = getReviewWithIngredientUseCase(menuName).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
