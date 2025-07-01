package com.jin.honey.feature.home.ui

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jin.feature.ui.state.DbState
import com.jin.feature.ui.state.SearchState
import com.jin.feature.ui.state.UiState
import com.jin.honey.feature.home.ui.content.FoodSearch
import com.jin.honey.feature.home.ui.content.HomeBanner
import com.jin.honey.feature.home.ui.content.HomeHeader
import com.jin.honey.feature.home.ui.content.HomeMenuCategory
import com.jin.honey.feature.home.ui.content.HomeRecommendMenu
import com.jin.honey.feature.home.ui.content.HomeRecommendRecipe
import com.jin.honey.feature.home.ui.content.HomeReviewRanking
import com.jin.honey.feature.home.ui.content.headercontent.LocationSearchBottomSheet
import com.jin.domain.model.address.Address
import com.jin.domain.model.address.SearchAddress
import com.jin.domain.model.food.CategoryType
import com.jin.domain.model.food.MenuPreview
import com.jin.domain.model.recipe.RecipePreview
import com.jin.domain.model.review.ReviewRankPreview

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToFoodCategory: (CategoryType) -> Unit,
    onNavigateToAddress: (searchAddress: SearchAddress) -> Unit,
    onNavigateToFoodSearch: (menus: List<MenuPreview>) -> Unit,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit
) {
    val context = LocalContext.current
    val addressSearchState by viewModel.searchAddressSearchState.collectAsState()
    val addressesState by viewModel.addressesState.collectAsState()
    val recommendMenusState by viewModel.recommendMenusState.collectAsState()
    val recommendRecipesState by viewModel.recommendRecipesState.collectAsState()
    val reviewRankState by viewModel.reviewRankingState.collectAsState()
    val categoryList by viewModel.categoryNameList.collectAsState()

    var addressSearchKeyword by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(addressSearchKeyword) {
        viewModel.searchAddressByKeyword(addressSearchKeyword)
    }

    LaunchedEffect(Unit) {
        viewModel.dbState.collect {
            when (it) {
                is DbState.Success -> {
                    Toast.makeText(context, "주소 변경 완료", Toast.LENGTH_SHORT)
                        .show()
                }

                is DbState.Error -> Toast.makeText(
                    context,
                    "주소 변경 실패. 다시 시도해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    val addresses = when (val state = addressesState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    LaunchedEffect(addresses) {
        showBottomSheet = addresses.isEmpty()
    }

    val recommendMenus = when (val state = recommendMenusState) {
        is UiState.Success -> state.data
        else -> emptyList()
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
        addresses = addresses,
        recommendMenus = recommendMenus,
        categoryNameList = categoryNameList,
        recommendRecipes = recommendRecipes,
        reviewRankList = reviewRankList,
        addressSearchKeyword = addressSearchKeyword,
        searchAddressSearchList = addressSearchList,
        showBottomSheet = showBottomSheet,
        onNavigateToFoodCategory = onNavigateToFoodCategory,
        onNavigateToAddress = onNavigateToAddress,
        onNavigateToFoodSearch = { onNavigateToFoodSearch(recommendMenus.orEmpty()) },
        onAddressQueryChanged = { addressSearchKeyword = it },
        onChangeSelectAddress = { viewModel.changedAddress(it) },
        onChangeBottomSheetState = { showBottomSheet = it },
        onNavigateToIngredient = onNavigateToIngredient,
        onNavigateToRecipe = onNavigateToRecipe
    )
}

@Composable
private fun CategorySuccessScreen(
    addresses: List<_root_ide_package_.com.jin.domain.model.address.Address>,
    recommendMenus: List<MenuPreview>,
    categoryNameList: List<String>?,
    recommendRecipes: List<RecipePreview>,
    reviewRankList: List<ReviewRankPreview>,
    addressSearchKeyword: String,
    searchAddressSearchList: List<SearchAddress>,
    showBottomSheet: Boolean,
    onNavigateToFoodCategory: (CategoryType) -> Unit,
    onNavigateToFoodSearch: () -> Unit,
    onAddressQueryChanged: (keyword: String) -> Unit,
    onNavigateToAddress: (searchAddress: SearchAddress) -> Unit,
    onChangeSelectAddress: (address: _root_ide_package_.com.jin.domain.model.address.Address) -> Unit,
    onChangeBottomSheetState: (isShow: Boolean) -> Unit,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
) {
    val currentAddress = addresses.find { it.isLatestAddress }

    LazyColumn(modifier = Modifier) {
        item {
            // 위치 지정
            HomeHeader(
                address = currentAddress,
                onBottomSheetClose = { onChangeBottomSheetState(it) }
            )
        }
        item {
            // search
            if (recommendMenus.isEmpty()) {
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
            if (reviewRankList.isEmpty()) {
                // FIXME 적절한 예외처리 필요
            } else {
                HomeReviewRanking(reviewRankList, onNavigateToIngredient)
            }
        }
        item {
            if (recommendRecipes.isEmpty()) {
                // FIXME 적절한 예외처리 필요
            } else {
                HomeRecommendRecipe(recommendRecipes, onNavigateToRecipe)
            }
        }
        item {
            HomeBanner()
        }
        item {
            if (recommendMenus.isEmpty()) {
                // FIXME 적절한 예외처리 필요
            } else {
                HomeRecommendMenu(recommendMenus, onNavigateToIngredient)
            }
        }
    }
    if (showBottomSheet) {
        LocationSearchBottomSheet(
            addresses = addresses,
            keyword = addressSearchKeyword,
            searchAddressSearchList = searchAddressSearchList,
            onBottomSheetClose = { onChangeBottomSheetState(it) },
            onAddressQueryChanged = onAddressQueryChanged,
            onNavigateToLocationDetail = onNavigateToAddress,
            onChangeSelectAddress = onChangeSelectAddress
        )
    } else {
        onAddressQueryChanged("")
    }
}
