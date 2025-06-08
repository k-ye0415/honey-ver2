package com.jin.honey.feature.category.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    categoryName: String,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
) {
    val context = LocalContext.current
    val categoryList by viewModel.allFoodList.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.saveState.collect {
            val toast = when (it) {
                is DbState.Success -> context.getString(R.string.cart_toast_save_success)
                is DbState.Error -> context.getString(R.string.cart_toast_save_error)
            }
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAllMenus()
    }

    when (val state = categoryList) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> CategorySuccessScreen(
            categoryName = categoryName,
            foodList = state.data,
            onNavigateToIngredient = onNavigateToIngredient,
            onNavigateToRecipe = onNavigateToRecipe,
            onInsertCart = { viewModel.insertIngredientToCart(it) })

        is UiState.Error -> CircularProgressIndicator()
    }
}

@Composable
private fun CategorySuccessScreen(
    categoryName: String,
    foodList: List<Food>,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
    onInsertCart: (cart: Cart) -> Unit,
) {
    val initialIndex = remember(foodList) {
        foodList.indexOfFirst { it.categoryType.categoryName == categoryName }
            .coerceAtLeast(0)
    }
    val pagerState = rememberPagerState(initialPage = initialIndex) { foodList.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton({}) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    "위치를 지정해야함",
                    textAlign = TextAlign.Center,
                )
                Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 4.dp
        ) {
            for ((index, category) in foodList.withIndex()) {
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

        HorizontalPager(state = pagerState) { page ->
            MenuListScreen(foodList[page].menu, onNavigateToIngredient, onNavigateToRecipe, onInsertCart)
        }
    }

}
