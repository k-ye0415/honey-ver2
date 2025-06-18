package com.jin.honey.feature.address.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.address.domain.model.SaveResult
import com.jin.honey.feature.address.domain.model.UserAddress
import com.jin.honey.feature.address.domain.usecase.SaveAddressUseCase
import com.jin.honey.feature.ui.state.DbState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddressViewModel(
    private val saveAddressUseCase: SaveAddressUseCase
) : ViewModel() {
    private val _insertState = MutableSharedFlow<DbState<Unit>>()
    val insertState = _insertState.asSharedFlow()

    fun saveAddress(userAddress: UserAddress, forceOverride: Boolean) {
        viewModelScope.launch {
            val result = saveAddressUseCase(userAddress, forceOverride).getOrNull()
                ?: _insertState.emit(DbState.Success)
            when (result) {
                is SaveResult.Saved -> _insertState.emit(DbState.Success)
                is SaveResult.Full -> _insertState.emit(DbState.Error(result.message))
                is SaveResult.Error -> _insertState.emit(DbState.Error(result.message))
            }
        }
    }
}
