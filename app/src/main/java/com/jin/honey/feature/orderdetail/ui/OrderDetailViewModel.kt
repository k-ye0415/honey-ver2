package com.jin.honey.feature.orderdetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.district.domain.model.UserAddress
import com.jin.honey.feature.district.domain.usecase.GetLatestAddressUseCase
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderDetailViewModel(private val getLatestAddressUseCase: GetLatestAddressUseCase) : ViewModel() {
    private val _latestAddressState = MutableStateFlow<UiState<UserAddress>>(UiState.Loading)
    val latestAddressState: StateFlow<UiState<UserAddress>> = _latestAddressState

    init {
        requestLatestAddress()
    }

    private fun requestLatestAddress() {
        viewModelScope.launch {
            _latestAddressState.value = getLatestAddressUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
