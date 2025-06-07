package com.jin.honey.feature.address.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.district.domain.model.SaveResult
import com.jin.honey.feature.district.domain.model.UserAddress
import com.jin.honey.feature.district.domain.usecase.SaveDistrictUseCase
import com.jin.honey.feature.ui.state.DbState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DistrictViewModel(
    private val saveDistrictUseCase: SaveDistrictUseCase
) : ViewModel() {
    private val _insertState = MutableSharedFlow<DbState<Unit>>()
    val insertState = _insertState.asSharedFlow()

    fun saveDistrict(userAddress: UserAddress, forceOverride: Boolean) {
        viewModelScope.launch {
            val result =
                saveDistrictUseCase(userAddress, forceOverride).getOrNull() ?: _insertState.emit(DbState.Error("Error"))
            when (result) {
                is SaveResult.Saved -> _insertState.emit(DbState.Success)
                is SaveResult.Full -> _insertState.emit(DbState.Error(result.message))
                is SaveResult.Error -> _insertState.emit(DbState.Error(result.message))
            }
        }
    }
}
