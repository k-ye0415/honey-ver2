package com.jin.honey.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.domain.address.model.Address
import com.jin.feature.ui.state.DbState
import com.jin.feature.ui.state.SearchState
import com.jin.feature.ui.state.UiState
import com.jin.domain.usecase.ChangeCurrentAddressUseCase
import com.jin.domain.usecase.GetAddressesUseCase
import com.jin.domain.usecase.SearchAddressUseCase
import com.jin.domain.usecase.GetCategoryNamesUseCase
import com.jin.domain.usecase.GetRecommendMenuUseCase
import com.jin.domain.usecase.GetRecommendRecipeUseCase
import com.jin.domain.usecase.GetRankingReviewUseCase
import com.jin.domain.address.model.SearchAddress
import com.jin.domain.food.model.MenuPreview
import com.jin.domain.model.recipe.RecipePreview
import com.jin.domain.model.review.ReviewRankPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCategoryNamesUseCase: GetCategoryNamesUseCase,
    private val searchAddressUseCase: SearchAddressUseCase,
    getAddressesUseCase: GetAddressesUseCase,
    private val getRecommendMenuUseCase: GetRecommendMenuUseCase,
    private val getRecommendRecipeUseCase: GetRecommendRecipeUseCase,
    private val getRankingReviewUseCase: GetRankingReviewUseCase,
    private val changeCurrentAddressUseCase: ChangeCurrentAddressUseCase
) : ViewModel() {

    val addressesState: StateFlow<UiState<List<Address>>> = getAddressesUseCase()
        .map { UiState.Success(it) }
        .catch { UiState.Error(it.message.orEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    private val _searchAddressSearchState = MutableStateFlow<SearchState<List<SearchAddress>>>(SearchState.Idle)
    val searchAddressSearchState: StateFlow<SearchState<List<SearchAddress>>> = _searchAddressSearchState

    private val _recommendMenusState = MutableStateFlow<UiState<List<MenuPreview>>>(UiState.Loading)
    val recommendMenusState: StateFlow<UiState<List<MenuPreview>>> = _recommendMenusState

    private val _recommendRecipesState = MutableStateFlow<UiState<List<RecipePreview>>>(UiState.Loading)
    val recommendRecipesState: StateFlow<UiState<List< RecipePreview>>> = _recommendRecipesState

    private val _reviewRankingState = MutableStateFlow<UiState<List<ReviewRankPreview>>>(UiState.Loading)
    val reviewRankingState: StateFlow<UiState<List< ReviewRankPreview>>> = _reviewRankingState

    private val _categoryNameList = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val categoryNameList: StateFlow<UiState<List<String>>> = _categoryNameList

    private val _dbState = MutableSharedFlow<DbState<Unit>>()
    val dbState = _dbState.asSharedFlow()

    init {
        launchCategoryTypeList()
        launchRecommendMenus()
        launchRecommendRecipe()
        launchReviewRanking()
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
            _searchAddressSearchState.value = SearchState.Idle
            return
        }
        viewModelScope.launch {
            _searchAddressSearchState.value = SearchState.Loading
            _searchAddressSearchState.value = searchAddressUseCase(keyword).fold(
                onSuccess = { SearchState.Success(it) },
                onFailure = { SearchState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun changedAddress(address: Address) {
        viewModelScope.launch {
            changeCurrentAddressUseCase(address).fold(
                onSuccess = { _dbState.emit(DbState.Success) },
                onFailure = { _dbState.emit(DbState.Error(it.message.orEmpty())) }
            )
        }
    }
}
