package com.jin.honey.feature.category.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.ui.theme.HoneyTheme
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen() {
    val categoryList = listOf(
        CategoryType.Burger,
        CategoryType.Chicken,
        CategoryType.Chinese,
        CategoryType.Japanese,
        CategoryType.Korean,
        CategoryType.Snack,
        CategoryType.Vegan,
        CategoryType.Dessert
    )
    val pagerState = rememberPagerState { categoryList.size }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 4.dp, // 양쪽 여백
            ) {
                categoryList.forEachIndexed { index, category ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
//                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(category.imageRes),
                                contentDescription = category.categoryName,
                                modifier = Modifier.size(28.dp)
                            )
                            Text(category.categoryName, fontSize = 8.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryScreenPreview() {
    HoneyTheme {
        CategoryScreen()
    }
}
