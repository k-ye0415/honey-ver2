package com.jin.honey.feature.address.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.district.domain.model.UserDistrict
import com.jin.honey.feature.district.domain.usecase.SaveDistrictUseCase
import com.jin.honey.feature.ui.state.DbState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DistrictViewModel(private val saveDistrictUseCase: SaveDistrictUseCase) : ViewModel() {
    private val _insertState = MutableSharedFlow<DbState>( )
    val insertState = _insertState.asSharedFlow()

    fun saveDistrict(district: UserDistrict) {
        viewModelScope.launch {
            val result = saveDistrictUseCase(district)
            if (result.isSuccess) {
                _insertState.emit(DbState.Success)
            } else {
                _insertState.emit(DbState.Error)
            }
        }
    }
}
