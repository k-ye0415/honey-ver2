package com.jin.honey.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.UserAddress
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.home.ui.content.FoodSearch
import com.jin.honey.feature.home.ui.content.HomeBanner
import com.jin.honey.feature.home.ui.content.HomeHeader
import com.jin.honey.feature.home.ui.content.HomeMenuCategory
import com.jin.honey.feature.home.ui.content.HomeRandomRecipe
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToFoodCategory: (CategoryType) -> Unit,
    onNavigateToAddress: (address: Address) -> Unit,
    onNavigateToFoodSearch: (menus: List<MenuPreview>) -> Unit,
) {
    val addressSearchState by viewModel.addressSearchState.collectAsState()
    val addressesState by viewModel.userAddressesState.collectAsState()
    val recommendMenusState by viewModel.recommendMenusState.collectAsState()
    val categoryList by viewModel.categoryNameList.collectAsState()

    var addressSearchKeyword by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.launchCategoryTypeList()
        viewModel.launchRecommendMenus()
    }

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

    val addressSearchList = when (val state = addressSearchState) {
        is SearchState.Success -> state.data
        else -> emptyList()
    }

    CategorySuccessScreen(
        userAddresses = userAddresses,
        recommendMenus = recommendMenus,
        categoryNameList = categoryNameList,
        addressSearchKeyword = addressSearchKeyword,
        addressSearchList = addressSearchList,
        onNavigateToFoodCategory = onNavigateToFoodCategory,
        onNavigateToAddress = onNavigateToAddress,
        onNavigateToFoodSearch = { onNavigateToFoodSearch(recommendMenus.orEmpty()) },
        onDistrictQueryChanged = { addressSearchKeyword = it },
    )
}

@Composable
//FIXME : UI 정리 시에 함수명 재정의 필요
private fun CategorySuccessScreen(
    userAddresses: List<UserAddress>,
    recommendMenus: List<MenuPreview>?,
    categoryNameList: List<String>?,
    addressSearchKeyword: String,
    addressSearchList: List<Address>,
    onNavigateToFoodCategory: (CategoryType) -> Unit,
    onNavigateToFoodSearch: () -> Unit,
    onDistrictQueryChanged: (keyword: String) -> Unit,
    onNavigateToAddress: (address: Address) -> Unit
) {
    LazyColumn(modifier = Modifier) {
        item {
            // 위치 지정
            HomeHeader(
                userAddresses,
                addressSearchKeyword,
                addressSearchList,
                onDistrictQueryChanged,
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
            HomeBanner()
        }
        item {
            HomeRandomRecipe()
        }
        item {
            // random food
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray)
            )


        }
    }
}
