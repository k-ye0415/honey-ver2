package com.jin.honey.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.address.domain.model.SearchAddress
import com.jin.honey.feature.address.domain.model.UserAddress
import com.jin.honey.feature.address.domain.usecase.GetAddressesUseCase
import com.jin.honey.feature.address.domain.usecase.SearchAddressUseCase
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.food.domain.usecase.GetCategoryNamesUseCase
import com.jin.honey.feature.food.domain.usecase.GetRecommendMenuUseCase
import com.jin.honey.feature.recipe.domain.GetRecommendRecipeUseCase
import com.jin.honey.feature.recipe.domain.model.RecipePreview
import com.jin.honey.feature.review.domain.GetRankingReviewUseCase
import com.jin.honey.feature.review.domain.ReviewRankPreview
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCategoryNamesUseCase: GetCategoryNamesUseCase,
    private val searchAddressUseCase: SearchAddressUseCase,
    private val getAddressesUseCase: GetAddressesUseCase,
    private val getRecommendMenuUseCase: GetRecommendMenuUseCase,
    private val getRecommendRecipeUseCase: GetRecommendRecipeUseCase,
    private val getRankingReviewUseCase: GetRankingReviewUseCase,
) : ViewModel() {
    private val _userAddressesState = MutableStateFlow<UiState<List<UserAddress>>>(UiState.Loading)
    val userAddressesState: StateFlow<UiState<List<UserAddress>>> = _userAddressesState

    private val _Search_addressSearchState = MutableStateFlow<SearchState<List<SearchAddress>>>(SearchState.Idle)
    val searchAddressSearchState: StateFlow<SearchState<List<SearchAddress>>> = _Search_addressSearchState

    private val _recommendMenusState = MutableStateFlow<UiState<List<MenuPreview>>>(UiState.Loading)
    val recommendMenusState: StateFlow<UiState<List<MenuPreview>>> = _recommendMenusState

    private val _recommendRecipesState = MutableStateFlow<UiState<List<RecipePreview>>>(UiState.Loading)
    val recommendRecipesState: StateFlow<UiState<List<RecipePreview>>> = _recommendRecipesState

    private val _reviewRankingState = MutableStateFlow<UiState<List<ReviewRankPreview>>>(UiState.Loading)
    val reviewRankingState: StateFlow<UiState<List<ReviewRankPreview>>> = _reviewRankingState

    private val _categoryNameList = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val categoryNameList: StateFlow<UiState<List<String>>> = _categoryNameList

    init {
        checkIfAddressesIsEmpty()
        launchCategoryTypeList()
        launchRecommendMenus()
        launchRecommendRecipe()
        launchReviewRanking()
    }

    private fun checkIfAddressesIsEmpty() {
        viewModelScope.launch {
            _userAddressesState.value = getAddressesUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    private fun launchCategoryTypeList() {
        viewModelScope.launch {
            _categoryNameList.value = getCategoryNamesUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    private fun launchRecommendMenus() {
        viewModelScope.launch {
            _recommendMenusState.value = getRecommendMenuUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    private fun launchRecommendRecipe() {
        viewModelScope.launch {
            _recommendRecipesState.value = getRecommendRecipeUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    private fun launchReviewRanking() {
        viewModelScope.launch {
            _reviewRankingState.value = getRankingReviewUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun searchAddressByKeyword(keyword: String) {
        if (keyword.isBlank()) {
            _Search_addressSearchState.value = SearchState.Idle
            return
        }
        viewModelScope.launch {
            _Search_addressSearchState.value = SearchState.Loading
            _Search_addressSearchState.value = searchAddressUseCase(keyword).fold(
                onSuccess = { SearchState.Success(it) },
                onFailure = { SearchState.Error(it.message.orEmpty()) }
            )
        }
    }
}
