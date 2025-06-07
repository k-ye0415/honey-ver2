package com.jin.honey.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.feature.district.domain.model.District
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.home.ui.content.FoodSearch
import com.jin.honey.feature.home.ui.content.HomeHeader
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToFoodCategory: (CategoryType) -> Unit,
    onNavigateToAddress: (district: District) -> Unit
) {
    val categoryList by viewModel.categoryNameList.collectAsState()
    val districtSearchState by viewModel.districtSearchState.collectAsState()

    var keyword by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.launchCategoryTypeList()
    }
    LaunchedEffect(keyword) {
        viewModel.searchDistrictByKeyword(keyword)
    }

    val categoryNameList = when (val state = categoryList) {
        is UiState.Loading -> emptyList()
        is UiState.Success -> state.data
        is UiState.Error -> null
    }

    val districtSearchList = when (val state = districtSearchState) {
        is SearchState.Success -> state.data
        else -> emptyList()
    }

    CategorySuccessScreen(
        categoryNameList = categoryNameList,
        keyword = keyword,
        districtSearchList = districtSearchList,
        onNavigateToFoodCategory = onNavigateToFoodCategory,
        onDistrictQueryChanged = { keyword = it },
        onNavigateToAddress
    )

}

@Composable
//FIXME : UI 정리 시에 함수명 재정의 필요
private fun CategorySuccessScreen(
    categoryNameList: List<String>?,
    keyword: String,
    districtSearchList: List<District>,
    onNavigateToFoodCategory: (CategoryType) -> Unit,
    onDistrictQueryChanged: (keyword: String) -> Unit,
    onNavigateToAddress: (district: District) -> Unit
) {
    LazyColumn(modifier = Modifier) {
        item {
            // 위치 지정
            HomeHeader(keyword, districtSearchList, onDistrictQueryChanged, onNavigateToAddress)
        }
        item {
            // search
            FoodSearch( )
        }
        item {
            if (categoryNameList.isNullOrEmpty()) {
                Text("ERROR")
            } else {
                CategoryListView(categoryNameList, onNavigateToFoodCategory)
            }
        }
        item {
            // banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray)
            )
        }
        item {

            // recipe
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
            )
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

@Composable
private fun CategoryListView(categoryType: List<String>, onNavigateToFoodCategory: (CategoryType) -> Unit) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        items(categoryType) { category ->
            val type = CategoryType.findByFirebaseDoc(category)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onNavigateToFoodCategory(type) }
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(type.imageRes),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
                Text(type.categoryName, fontSize = 8.sp)
            }
        }
    }
}
