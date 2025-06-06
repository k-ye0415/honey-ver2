package com.jin.honey.feature.address.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.district.domain.model.UserDistrict
import com.jin.honey.feature.district.domain.usecase.SaveDistrictUseCase
import kotlinx.coroutines.launch

class DistrictViewModel(private val saveDistrictUseCase: SaveDistrictUseCase) : ViewModel() {

    fun saveDistrict(district: UserDistrict) {
        viewModelScope.launch {
            saveDistrictUseCase(district)
        }
    }
}
