package com.jin.honey.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.ui.state.UiState

@Composable
fun HomeScreen(viewModel: HomeViewModel, onNavigateToFoodCategory: (CategoryType) -> Unit) {
    val categoryList by viewModel.categoryList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.launchCategoryTypeList()
    }

    when (val state = categoryList) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> CategorySuccessScreen(state.data, onNavigateToFoodCategory)
        is UiState.Error -> CategorySuccessScreen(null, onNavigateToFoodCategory)
    }


}

@Composable
private fun CategorySuccessScreen(categoryType: List<String>?, onNavigateToFoodCategory: (CategoryType) -> Unit) {
    LazyColumn(modifier = Modifier) {
        item {
            // 위치 지정
            Row {
                Text("주소가 필요해요")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
            // search
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.LightGray)
            )
            if (categoryType.isNullOrEmpty()) {
                CircularProgressIndicator()
            } else {
                CategoryListView(categoryType, onNavigateToFoodCategory)
            }
            // banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray)
            )
            // recipe
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
            )
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
