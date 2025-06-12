package com.jin.honey.feature.reviewwrite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.payment.domain.model.Payment
import com.jin.honey.feature.payment.domain.usecase.GetOrderDetailUseCase
import com.jin.honey.feature.review.domain.Review
import com.jin.honey.feature.review.domain.WriteReviewUseCase
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ReviewWriteViewModel(
    private val getOrderDetailUseCase: GetOrderDetailUseCase,
    private val writeReviewUseCase: WriteReviewUseCase
) : ViewModel() {
    private val _orderDetailState = MutableStateFlow<UiState<Payment>>(UiState.Loading)
    val orderDetailState: StateFlow<UiState<Payment>> = _orderDetailState

    private val _insertState = MutableSharedFlow<DbState<Unit>>()
    val insertState = _insertState.asSharedFlow()

    fun fetchOrderDetail(orderKey: String) {
        viewModelScope.launch {
            _orderDetailState.value = getOrderDetailUseCase(orderKey).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun writeReview(reviews: List<Review>) {
        viewModelScope.launch {
            writeReviewUseCase(reviews).fold(
                onSuccess = { _insertState.emit(DbState.Success) },
                onFailure = { _insertState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }
}
