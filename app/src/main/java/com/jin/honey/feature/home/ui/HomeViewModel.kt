package com.jin.honey.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.UserAddress
import com.jin.honey.feature.district.domain.usecase.GetAddressesUseCase
import com.jin.honey.feature.district.domain.usecase.SearchAddressUseCase
import com.jin.honey.feature.food.domain.usecase.GetCategoryNamesUseCase
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCategoryNamesUseCase: GetCategoryNamesUseCase,
    private val searchAddressUseCase: SearchAddressUseCase,
    private val getAddressesUseCase: GetAddressesUseCase,
) : ViewModel() {
    private val _userAddressesState = MutableStateFlow<UiState<List<UserAddress>>>(UiState.Loading)
    val userAddressesState: StateFlow<UiState<List<UserAddress>>> = _userAddressesState

    private val _addressSearchState = MutableStateFlow<SearchState<List<Address>>>(SearchState.Idle)
    val addressSearchState: StateFlow<SearchState<List<Address>>> = _addressSearchState

    private val _categoryNameList = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val categoryNameList: StateFlow<UiState<List<String>>> = _categoryNameList

    init {
        checkIfAddressesIsEmpty()
    }

    private fun checkIfAddressesIsEmpty() {
        viewModelScope.launch {
            _userAddressesState.value = getAddressesUseCase().fold(
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
