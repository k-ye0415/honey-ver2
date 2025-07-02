package com.jin.ui.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.domain.address.model.Address
import com.jin.feature.ui.state.DbState
import com.jin.domain.usecase.SaveAddressUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddressViewModel(
    private val saveDistrictUseCase: SaveAddressUseCase
) : ViewModel() {
    private val _insertState = MutableSharedFlow<DbState<Unit>>()
    val insertState = _insertState.asSharedFlow()

    fun saveAddress(address: Address, forceOverride: Boolean) {
        viewModelScope.launch {
            saveDistrictUseCase(address, forceOverride).fold(
                onSuccess = { _insertState.emit(DbState.Success) },
                onFailure = { _insertState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }
}
