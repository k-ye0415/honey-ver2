package com.jin.honey.feature.reviewwrite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.payment.domain.model.Payment
import com.jin.honey.feature.payment.domain.usecase.GetOrderDetailUseCase
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewWriteViewModel(private val getOrderDetailUseCase: GetOrderDetailUseCase) : ViewModel() {
    private val _orderDetailState = MutableStateFlow<UiState<Payment>>(UiState.Loading)
    val orderDetailState: StateFlow<UiState<Payment>> = _orderDetailState

    fun fetchOrderDetail(orderKey: String) {
        viewModelScope.launch {
            _orderDetailState.value = getOrderDetailUseCase(orderKey).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
