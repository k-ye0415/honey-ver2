package com.jin.honey.feature.category.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jin.honey.ui.theme.HoneyTheme

@Composable
fun CategoryScreen() {
    val pagerState = rememberPagerState { 8 }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = pagerState.currentPage) {

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
