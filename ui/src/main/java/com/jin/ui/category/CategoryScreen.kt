package com.jin.ui.category

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.state.DbState
import com.jin.state.SearchState
import com.jin.state.UiState
import com.jin.domain.address.model.Address
import com.jin.domain.address.model.SearchAddress
import com.jin.domain.cart.model.Cart
import com.jin.domain.food.model.Food
import com.jin.drawableRes
import com.jin.ui.R
import com.jin.ui.address.LocationSearchBottomSheet
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    categoryName: String,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToAddressDetail: (searchAddress: SearchAddress) -> Unit,
) {
    val context = LocalContext.current
    val userAddressState by viewModel.addressesState.collectAsState()
    val addressSearchState by viewModel.searchAddressSearchState.collectAsState()
    val foodsState by viewModel.allFoods.collectAsState()
    val favoriteList by viewModel.saveFavoriteState.collectAsState()

    var addressSearchKeyword by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.saveCartState.collect {
            val toast = when (it) {
                is DbState.Success -> context.getString(R.string.cart_toast_save_success)
                is DbState.Error -> context.getString(R.string.cart_toast_save_error)
            }
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.addressChangeState.collect {
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
            showBottomSheet = false
        }
    }

    LaunchedEffect(addressSearchKeyword) {
        viewModel.searchAddressByKeyword(addressSearchKeyword)
    }

    val foodList = when (val state = foodsState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val useAddress = when (val state = userAddressState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val addressSearchList = when (val state = addressSearchState) {
        is SearchState.Success -> state.data
        else -> emptyList()
    }

    CategorySuccessScreen(
        useAddressList = useAddress,
        searchSearchAddressList = addressSearchList,
        addressSearchKeyword = addressSearchKeyword,
        categoryName = categoryName,
        foodList = foodList,
        favoriteList = favoriteList,
        showBottomSheet = showBottomSheet,
        onNavigateToIngredient = onNavigateToIngredient,
        onNavigateToRecipe = onNavigateToRecipe,
        onInsertCart = { viewModel.insertIngredientToCart(cart = it) },
        onClickFavorite = { viewModel.toggleFavoriteMenu(menuName = it) },
        onNavigateToHome = onNavigateToHome,
        onNavigateToAddressDetail = onNavigateToAddressDetail,
        onAddressQueryChanged = { addressSearchKeyword = it },
        onChangeSelectAddress = { viewModel.changedAddress(it) },
        onChangeBottomSheetState = { showBottomSheet = it }
    )
}

@Composable
private fun CategorySuccessScreen(
    useAddressList: List<Address>,
    searchSearchAddressList: List<SearchAddress>,
    addressSearchKeyword: String,
    categoryName: String,
    foodList: List<Food>,
    favoriteList: List<String>,
    showBottomSheet: Boolean,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
    onInsertCart: (cart: Cart) -> Unit,
    onClickFavorite: (menuName: String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToAddressDetail: (searchAddress: SearchAddress) -> Unit,
    onAddressQueryChanged: (keyword: String) -> Unit,
    onChangeSelectAddress: (address: Address) -> Unit,
    onChangeBottomSheetState: (isShow: Boolean) -> Unit,
) {
    val initialIndex = remember(foodList) {
        foodList.indexOfFirst { it.categoryType.categoryName == categoryName }
            .coerceAtLeast(0)
    }
    val pagerState = rememberPagerState(initialPage = initialIndex) { foodList.size }
    val coroutineScope = rememberCoroutineScope()
    var showLocationBottomSheet by remember { mutableStateOf(false) }

    val currentAddress = useAddressList.find { it.isLatestAddress }?.address?.addressName?.lotNumAddress
        ?: stringResource(R.string.order_detail_need_to_address)

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
                    text = currentAddress,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable { onChangeBottomSheetState(true) }
                )
                Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            edgePadding = 4.dp,
            indicator = { },
        ) {
            for ((index, category) in foodList.withIndex()) {
                val isSelected = pagerState.currentPage == index
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isSelected) Color(0xFFffe6ee) else Color.Transparent)
                                .size(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(category.categoryType.drawableRes()),
                                contentDescription = category.categoryType.categoryName,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Text(
                            category.categoryType.categoryName,
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        HorizontalPager(state = pagerState) { page ->
            CategoryMenuScreen(
                foodList[page].menu,
                foodList[page].categoryType,
                favoriteList,
                onNavigateToIngredient,
                onNavigateToRecipe,
                onInsertCart,
                onClickFavorite
            )
        }

        if (showBottomSheet) {
            LocationSearchBottomSheet(
                addresses = useAddressList,
                keyword = addressSearchKeyword,
                searchAddressSearchList = searchSearchAddressList,
                onBottomSheetClose = { showLocationBottomSheet = it },
                onAddressQueryChanged = onAddressQueryChanged,
                onNavigateToLocationDetail = onNavigateToAddressDetail,
                onChangeSelectAddress = onChangeSelectAddress
            )
        }
    }

}
