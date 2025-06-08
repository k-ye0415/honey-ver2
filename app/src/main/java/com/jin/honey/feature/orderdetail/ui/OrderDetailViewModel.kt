package com.jin.honey.feature.orderdetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.UserAddress
import com.jin.honey.feature.district.domain.usecase.GetLatestAddressUseCase
import com.jin.honey.feature.district.domain.usecase.SearchAddressUseCase
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderDetailViewModel(
    private val getLatestAddressUseCase: GetLatestAddressUseCase,
    private val searchAddressUseCase: SearchAddressUseCase,
) : ViewModel() {
    private val _latestAddressState = MutableStateFlow<UiState<UserAddress>>(UiState.Loading)
    val latestAddressState: StateFlow<UiState<UserAddress>> = _latestAddressState

    private val _addressSearchState = MutableStateFlow<SearchState<List<Address>>>(SearchState.Idle)
    val addressSearchState: StateFlow<SearchState<List<Address>>> = _addressSearchState

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

    fun searchAddressByKeyword(keyword: String) {
        if (keyword.isBlank()) {
            _addressSearchState.value = SearchState.Idle
            return
        }
        viewModelScope.launch {
            _addressSearchState.value = SearchState.Loading
            _addressSearchState.value = searchAddressUseCase(keyword).fold(
                onSuccess = { SearchState.Success(it) },
                onFailure = { SearchState.Error(it.message.orEmpty()) }
            )
        }
    }
}
