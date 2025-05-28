package com.jin.honey.main.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jin.honey.feature.favorite.ui.FavoriteScreen
import com.jin.honey.feature.favorite.ui.FavoriteViewModel
import com.jin.honey.feature.home.ui.HomeScreen
import com.jin.honey.feature.home.ui.HomeViewModel
import com.jin.honey.feature.mypage.ui.MyPageScreen
import com.jin.honey.feature.mypage.ui.MyPageViewModel
import com.jin.honey.feature.order.ui.OrderScreen
import com.jin.honey.feature.order.ui.OrderViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    orderViewModel: OrderViewModel,
    favoriteViewModel: FavoriteViewModel,
    myPageViewModel: MyPageViewModel,
    onNavigateToFoodCategory: () -> Unit,
) {
    val pagerState = rememberPagerState { TabMenu.entries.size }
    val coroutineScope = rememberCoroutineScope()
    val navigationBarHeightDp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.toInt().dp
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = navigationBarHeightDp),
        bottomBar = {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                for ((index, tab) in TabMenu.entries.withIndex()) {
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }
                    ) {
                        Icon(imageVector = tab.icon, contentDescription = tab.title)
                        Text(tab.title)
                    }
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) { page ->
            when (TabMenu.entries[page]) {
                TabMenu.HOME -> HomeScreen(homeViewModel, onNavigateToFoodCategory)
                TabMenu.ORDER -> OrderScreen(orderViewModel)
                TabMenu.FAVORITE -> FavoriteScreen(favoriteViewModel)
                else -> MyPageScreen(myPageViewModel)
            }
        }
    }
}

enum class TabMenu(val title: String, val icon: ImageVector) {
    HOME("home", Icons.Default.Home),
    ORDER("order", Icons.Default.Payments),
    FAVORITE("Favorite", Icons.Default.FavoriteBorder),
    MY_PAGE("myPage", Icons.Default.Person)
}
