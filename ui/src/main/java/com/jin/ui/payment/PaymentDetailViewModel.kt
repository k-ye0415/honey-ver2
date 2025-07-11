package com.jin.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.domain.order.model.Order
import com.jin.domain.usecase.GetOrderDetailUseCase
import com.jin.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentDetailViewModel(private val getOrderDetailUseCase: GetOrderDetailUseCase) : ViewModel() {
    private val _orderDetailState = MutableStateFlow<UiState<Order>>(UiState.Loading)
    val orderDetailState: StateFlow<UiState<Order>> = _orderDetailState

    fun fetchOrderDetail(orderKey: String) {
        viewModelScope.launch {
            _orderDetailState.value = getOrderDetailUseCase(orderKey).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
