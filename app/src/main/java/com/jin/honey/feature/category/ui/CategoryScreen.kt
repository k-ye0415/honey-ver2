package com.jin.honey.feature.category.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.UserAddress
import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.home.ui.content.headercontent.LocationSearchBottomSheet
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    categoryName: String,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToAddressDetail: (address: Address) -> Unit,
) {
    val context = LocalContext.current
    val userAddressState by viewModel.userAddressesState.collectAsState()
    val addressSearchState by viewModel.addressSearchState.collectAsState()
    val categoryList by viewModel.allFoodList.collectAsState()
    val favoriteList by viewModel.saveFavoriteState.collectAsState()

    var addressSearchKeyword by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.saveCartState.collect {
            val toast = when (it) {
                is DbState.Success -> context.getString(R.string.cart_toast_save_success)
                is DbState.Error -> context.getString(R.string.cart_toast_save_error)
            }
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(addressSearchKeyword) {
        viewModel.searchAddressByKeyword(addressSearchKeyword)
    }

    val useAddress = when (val state = userAddressState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val addressSearchList = when (val state = addressSearchState) {
        is SearchState.Success -> state.data
        else -> emptyList()
    }

    when (val state = categoryList) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> CategorySuccessScreen(
            useAddressList = useAddress,
            searchAddressList = addressSearchList,
            addressSearchKeyword = addressSearchKeyword,
            categoryName = categoryName,
            foodList = state.data,
            favoriteList = favoriteList,
            onNavigateToIngredient = onNavigateToIngredient,
            onNavigateToRecipe = onNavigateToRecipe,
            onInsertCart = { viewModel.insertIngredientToCart(cart = it) },
            onClickFavorite = { viewModel.toggleFavoriteMenu(menuName = it) },
            onNavigateToHome = onNavigateToHome,
            onNavigateToAddressDetail = onNavigateToAddressDetail,
            onAddressQueryChanged = { addressSearchKeyword = it }
        )

        is UiState.Error -> CircularProgressIndicator()
    }
}

@Composable
private fun CategorySuccessScreen(
    useAddressList: List<UserAddress>,
    searchAddressList: List<Address>,
    addressSearchKeyword: String,
    categoryName: String,
    foodList: List<Food>,
    favoriteList: List<String>,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
    onInsertCart: (cart: Cart) -> Unit,
    onClickFavorite: (menuName: String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToAddressDetail: (address: Address) -> Unit,
    onAddressQueryChanged: (keyword: String) -> Unit,
) {
    val initialIndex = remember(foodList) {
        foodList.indexOfFirst { it.categoryType.categoryName == categoryName }
            .coerceAtLeast(0)
    }
    val pagerState = rememberPagerState(initialPage = initialIndex) { foodList.size }
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val userAddress = if (useAddressList.isEmpty()) {
        stringResource(R.string.order_detail_need_to_address)
    } else {
        useAddressList.firstOrNull()?.address?.addressName?.lotNumAddress
            ?: stringResource(R.string.order_detail_need_to_address)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(onClick = onNavigateToHome) {
                Icon(
                    Icons.Default.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.ingredient_back_icon_desc)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = userAddress,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable { showBottomSheet = true }
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
            MenuListScreen(
                foodList[page].menu,
                favoriteList,
                onNavigateToIngredient,
                onNavigateToRecipe,
                onInsertCart,
                onClickFavorite
            )
        }

        if (showBottomSheet) {
            LocationSearchBottomSheet(
                userAddresses = useAddressList,
                keyword = addressSearchKeyword,
                addressSearchList = searchAddressList,
                onBottomSheetClose = { showBottomSheet = it },
                onAddressQueryChanged = onAddressQueryChanged,
                onNavigateToLocationDetail = onNavigateToAddressDetail
            )
        }
    }

}
