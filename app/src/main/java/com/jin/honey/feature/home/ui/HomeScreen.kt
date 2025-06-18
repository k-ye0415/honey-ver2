package com.jin.honey.feature.home.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jin.honey.feature.address.domain.model.SearchAddress
import com.jin.honey.feature.address.domain.model.Address
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.home.ui.content.FoodSearch
import com.jin.honey.feature.home.ui.content.HomeBanner
import com.jin.honey.feature.home.ui.content.HomeHeader
import com.jin.honey.feature.home.ui.content.HomeMenuCategory
import com.jin.honey.feature.home.ui.content.HomeRecommendMenu
import com.jin.honey.feature.home.ui.content.HomeRecommendRecipe
import com.jin.honey.feature.home.ui.content.HomeReviewRanking
import com.jin.honey.feature.recipe.domain.model.RecipePreview
import com.jin.honey.feature.review.domain.ReviewRankPreview
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToFoodCategory: (CategoryType) -> Unit,
    onNavigateToAddress: (searchAddress: SearchAddress) -> Unit,
    onNavigateToFoodSearch: (menus: List<MenuPreview>) -> Unit,
) {
    val addressSearchState by viewModel.searchAddressSearchState.collectAsState()
    val addressesState by viewModel.addressesState.collectAsState()
    val recommendMenusState by viewModel.recommendMenusState.collectAsState()
    val recommendRecipesState by viewModel.recommendRecipesState.collectAsState()
    val reviewRankState by viewModel.reviewRankingState.collectAsState()
    val categoryList by viewModel.categoryNameList.collectAsState()

    var addressSearchKeyword by remember { mutableStateOf("") }

    LaunchedEffect(addressSearchKeyword) {
        viewModel.searchAddressByKeyword(addressSearchKeyword)
    }

    val userAddresses = when (val state = addressesState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val recommendMenus = when (val state = recommendMenusState) {
        is UiState.Loading -> emptyList()
        is UiState.Success -> state.data
        is UiState.Error -> null
    }

    val categoryNameList = when (val state = categoryList) {
        is UiState.Loading -> emptyList()
        is UiState.Success -> state.data
        is UiState.Error -> null
    }

    val recommendRecipes = when (val state = recommendRecipesState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val reviewRankList = when (val state = reviewRankState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val addressSearchList = when (val state = addressSearchState) {
        is SearchState.Success -> state.data
        else -> emptyList()
    }

    CategorySuccessScreen(
        addresses = userAddresses,
        recommendMenus = recommendMenus,
        categoryNameList = categoryNameList,
        recommendRecipes = recommendRecipes,
        reviewRankList = reviewRankList,
        addressSearchKeyword = addressSearchKeyword,
        searchAddressSearchList = addressSearchList,
        onNavigateToFoodCategory = onNavigateToFoodCategory,
        onNavigateToAddress = onNavigateToAddress,
        onNavigateToFoodSearch = { onNavigateToFoodSearch(recommendMenus.orEmpty()) },
        onAddressQueryChanged = { addressSearchKeyword = it },
    )
}

@Composable
//FIXME : UI 정리 시에 함수명 재정의 필요
private fun CategorySuccessScreen(
    addresses: List<Address>,
    recommendMenus: List<MenuPreview>?,
    categoryNameList: List<String>?,
    recommendRecipes: List<RecipePreview>,
    reviewRankList: List<ReviewRankPreview>,
    addressSearchKeyword: String,
    searchAddressSearchList: List<SearchAddress>,
    onNavigateToFoodCategory: (CategoryType) -> Unit,
    onNavigateToFoodSearch: () -> Unit,
    onAddressQueryChanged: (keyword: String) -> Unit,
    onNavigateToAddress: (searchAddress: SearchAddress) -> Unit
) {
    LazyColumn(modifier = Modifier) {
        item {
            // 위치 지정
            HomeHeader(
                addresses,
                addressSearchKeyword,
                searchAddressSearchList,
                onAddressQueryChanged,
                onNavigateToAddress
            )
        }
        item {
            // search
            if (recommendMenus.isNullOrEmpty()) {
                // FIXME 적절한 예외처리 필요
            } else {
                FoodSearch(recommendMenus, onNavigateToFoodSearch)
            }
        }
        item {
            if (categoryNameList.isNullOrEmpty()) {
                // FIXME 적절한 예외처리 필요
            } else {
                HomeMenuCategory(categoryNameList, onNavigateToFoodCategory)
            }
        }
        item {
            if (reviewRankList.isNotEmpty()) {
                HomeReviewRanking(reviewRankList)
            }
        }
        item {
            HomeRecommendRecipe(recommendRecipes)
        }
        item {
            HomeBanner()
        }
        item {
            HomeRecommendMenu(recommendMenus ?: emptyList())
        }
    }
}
