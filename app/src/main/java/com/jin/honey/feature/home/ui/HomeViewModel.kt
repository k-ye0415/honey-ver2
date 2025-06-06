package com.jin.honey.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jin.honey.feature.district.domain.model.District
import com.jin.honey.feature.district.domain.model.UserDistrict
import com.jin.honey.feature.district.domain.usecase.GetDistrictUseCase
import com.jin.honey.feature.district.domain.usecase.SearchDistrictUseCase
import com.jin.honey.feature.food.domain.usecase.GetCategoryNamesUseCase
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCategoryNamesUseCase: GetCategoryNamesUseCase,
    private val searchDistrictUseCase: SearchDistrictUseCase,
    private val getDistrictUseCase: GetDistrictUseCase,
) : ViewModel() {
    private val _districtsState = MutableStateFlow<UiState<List<UserDistrict>>>(UiState.Loading)
    val districtsState: StateFlow<UiState<List<UserDistrict>>> = _districtsState

    private val _districtSearchState = MutableStateFlow<SearchState<List<District>>>(SearchState.Idle)
    val districtSearchState: StateFlow<SearchState<List<District>>> = _districtSearchState

    private val _categoryNameList = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val categoryNameList: StateFlow<UiState<List<String>>> = _categoryNameList

    init {
        checkDistrict()
    }

    private fun checkDistrict() {
        viewModelScope.launch {
            _districtsState.value = getDistrictUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun launchCategoryTypeList() {
        viewModelScope.launch {
            _categoryNameList.value = getCategoryNamesUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun searchDistrictByKeyword(keyword: String) {
        if (keyword.isBlank()) {
            _districtSearchState.value = SearchState.Idle
            return
        }
        viewModelScope.launch {
            _districtSearchState.value = SearchState.Loading
            _districtSearchState.value = searchDistrictUseCase(keyword).fold(
                onSuccess = { SearchState.Success(it) },
                onFailure = { SearchState.Error(it.message.orEmpty()) }
            )
        }
    }
}
