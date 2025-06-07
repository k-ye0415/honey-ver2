package com.jin.honey.feature.address.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.district.domain.model.UserDistrict
import com.jin.honey.feature.district.domain.usecase.DeleteAndSaveUseCase
import com.jin.honey.feature.district.domain.usecase.SaveDistrictUseCase
import com.jin.honey.feature.ui.state.DbState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DistrictViewModel(
    private val saveDistrictUseCase: SaveDistrictUseCase,
    private val deleteAndSaveUseCase: DeleteAndSaveUseCase
) : ViewModel() {
    private val _insertState = MutableSharedFlow<DbState<Unit>>()
    val insertState = _insertState.asSharedFlow()

    fun saveDistrict(district: UserDistrict) {
        viewModelScope.launch {
            saveDistrictUseCase(district).fold(
                onSuccess = { _insertState.emit(DbState.Success) },
                onFailure = { _insertState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }

    fun deleteAndSaveDistrict(district: UserDistrict) {
        viewModelScope.launch {
            deleteAndSaveUseCase(district).fold(
                onSuccess = { _insertState.emit(DbState.Success) },
                onFailure = { _insertState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }
}
