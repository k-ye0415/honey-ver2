package com.jin.honey.feature.category.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.HoneyTheme
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    categoryName: String,
) {
    val categoryList by viewModel.allCategoryList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllMenus()
    }

    when (val state = categoryList) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> CategorySuccessScreen(categoryName, state.data)
        is UiState.Error -> CircularProgressIndicator()
    }
}

@Composable
private fun CategorySuccessScreen(categoryName: String, categoryList: List<Category>) {
    val initialIndex = remember(categoryList) {
        categoryList.indexOfFirst { it.categoryType.categoryName == categoryName }
            .coerceAtLeast(0)
    }
    val pagerState = rememberPagerState(initialPage = initialIndex) { categoryList.size }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 4.dp
            ) {
                categoryList.forEachIndexed { index, category ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(category.categoryType.imageRes),
                                contentDescription = category.categoryType.categoryName,
                                modifier = Modifier.size(28.dp)
                            )
                            Text(category.categoryType.categoryName, fontSize = 8.sp)
                        }
                    }
                }
            }

            // 예: Pager 내용 (선택한 탭에 따른 화면)
            HorizontalPager(state = pagerState) { page ->
                Text("선택된 카테고리: ${categoryList[page].categoryType.categoryName}")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryScreenPreview() {
    HoneyTheme {
//        CategoryScreen()
    }
}
