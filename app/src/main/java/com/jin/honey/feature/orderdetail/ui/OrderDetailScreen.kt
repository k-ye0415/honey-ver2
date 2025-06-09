package com.jin.honey.feature.orderdetail.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.home.ui.content.headercontent.LocationSearchBottomSheet
import com.jin.honey.feature.order.ui.content.cart.content.CartOptionModifyBottomSheet
import com.jin.honey.feature.orderdetail.ui.content.OrderDetailCartItems
import com.jin.honey.feature.orderdetail.ui.content.OrderAddress
import com.jin.honey.feature.orderdetail.ui.content.OrderDetailHeader
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.FoodSearchBoxBorderColor
import com.jin.honey.ui.theme.OrderDetailBoxBorderColor
import com.jin.honey.ui.theme.OrderDetailDeleteIconColor
import com.jin.honey.ui.theme.OrderDetailPaymentBoxBackgroundColor
import com.jin.honey.ui.theme.OrderDetailRequirementCheckedColor
import com.jin.honey.ui.theme.OrderDetailRequirementHintColor
import com.jin.honey.ui.theme.PointColor

@Composable
fun OrderDetailScreen(
    viewModel: OrderDetailViewModel,
    onNavigateToLocationDetail: (address: Address) -> Unit,
    onNavigateToCategory: () -> Unit
) {
    val context = LocalContext.current
    val latestAddressState by viewModel.latestAddressState.collectAsState()
    val addressSearchState by viewModel.addressSearchState.collectAsState()
    val cartItemsState by viewModel.cartItemState.collectAsState()

    var showAddressBottomSheet by remember { mutableStateOf(false) }
    var showOptionModifyBottomSheet by remember { mutableStateOf(false) }

    var addressSearchKeyword by remember { mutableStateOf("") }
    var requirementsContent by remember { mutableStateOf("") }
    var requirementsChecked by remember { mutableStateOf(true) }

    val latestAddress = when (val state = latestAddressState) {
        is UiState.Success -> state.data
        else -> null
    }

    val addressSearchList = when (val state = addressSearchState) {
        is SearchState.Success -> state.data
        else -> emptyList()
    }

    val cartItems = when (val state = cartItemsState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    LaunchedEffect(addressSearchKeyword) {
        viewModel.searchAddressByKeyword(addressSearchKeyword)
    }

    LaunchedEffect(Unit) {
        viewModel.updateState.collect {
            when (it) {
                is DbState.Success -> Toast.makeText(
                    context,
                    context.getString(R.string.cart_toast_update_success),
                    Toast.LENGTH_SHORT
                ).show()

                is DbState.Error -> Toast.makeText(
                    context,
                    context.getString(R.string.cart_toast_update_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
        LazyColumn(modifier = Modifier.padding(innerpadding)) {
            item {
                OrderDetailHeader()
            }
            item {
                OrderAddress(
                    latestAddress = latestAddress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp)
                        .padding(horizontal = 10.dp),
                    onChangedAddress = { showAddressBottomSheet = true }
                )
            }
            item {
                OrderDetailCartItems(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    cartItems = cartItems,
                    onShowOptionBottomSheet = { showOptionModifyBottomSheet = true },
                    onDeleteMenu = { viewModel.removeMenuInCartItem(it) },
                    onDeleteIngredient = { cartItem, ingredientName ->
                        viewModel.removeIngredientInCartItem(
                            cartItem,
                            ingredientName
                        )
                    }
                )
            }

            item {
                OrderDetailRequirements(
                    content = requirementsContent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    onContentChanged = { requirementsContent = it }

                )
            }
            item {
                OrderDetailPayment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp)
                )
            }
            item {
                var totalPrice = 0
                for (item in cartItems) {
                    for (ingredient in item.ingredients) {
                        totalPrice += ingredient.unitPrice * ingredient.cartQuantity
                    }
                }
                OrderDetailPrice(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    totalPrice = totalPrice,
                )
            }
            item {
                OrderDetailAgreeToTerms(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                )
            }
            item {
                OrderDetailOrderButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    menuCount = cartItems.count(),
                )
            }
        }
        if (showAddressBottomSheet) {
            LocationSearchBottomSheet(
                userAddresses = if (latestAddress != null) listOf(latestAddress) else emptyList(),
                keyword = addressSearchKeyword,
                addressSearchList = addressSearchList,
                onBottomSheetClose = { showAddressBottomSheet = it },
                onLocationQueryChanged = { addressSearchKeyword = it },
                onNavigateToLocationDetail = onNavigateToLocationDetail
            )
        } else {
            addressSearchKeyword = ""
        }
        if (showOptionModifyBottomSheet) {
            CartOptionModifyBottomSheet(
                cartItems = cartItems,
                onRemoveCart = { cartItem, ingredientName ->
                    viewModel.removeIngredientInCartItem(
                        cartItem,
                        ingredientName
                    )
                },
                onBottomSheetClose = { showOptionModifyBottomSheet = it },
                onChangeOption = { viewModel.modifyCartQuantity(it) },
            )
        }
    }
}


@Composable
private fun OrderDetailRequirements(
    content: String,
    modifier: Modifier,
    onContentChanged: (newContent: String) -> Unit
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
    ) {
        Column {
            Text(
                text = stringResource(R.string.order_detail_requirements),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 14.dp, bottom = 10.dp)
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, FoodSearchBoxBorderColor, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 10.dp),
            ) {
                BasicTextField(
                    value = content,
                    onValueChange = { onContentChanged(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
//                            .focusRequester(focusRequester)
                        .onFocusChanged { },
                    decorationBox = { innerTextField ->
                        if (content.isEmpty()) {
                            Text(
                                text = stringResource(R.string.order_detail_requirements_hint),
                                color = OrderDetailRequirementHintColor,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = true,
                    onCheckedChange = { },
                    colors = CheckboxDefaults.colors(checkedColor = OrderDetailRequirementCheckedColor)
                )
                Text(
                    text = stringResource(R.string.order_detail_requirements_recycle),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_environment),
                    contentDescription = stringResource(R.string.order_detail_requirements_recycle_icon_desc),
                    modifier = Modifier.size(12.dp),
                    tint = Color.Unspecified
                )
            }
            HorizontalDivider()
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.order_detail_rider_requirements),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(text = stringResource(R.string.order_detail_rider_requirements_nothing), fontSize = 14.sp)
                Icon(
                    Icons.Default.ArrowForwardIos,
                    contentDescription = stringResource(R.string.order_detail_change_rider_requirements_icon_desc),
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(12.dp)
                )
            }
        }
    }
}

@Composable
private fun OrderDetailPayment(modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp)) {
            Row(modifier = Modifier.padding(bottom = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.order_detail_payment),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(text = stringResource(R.string.order_detail_change_payment), fontSize = 14.sp)
                Icon(
                    Icons.Default.ArrowForwardIos,
                    contentDescription = stringResource(R.string.order_detail_change_payment_icon_desc),
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(12.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(OrderDetailPaymentBoxBackgroundColor)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(stringResource(R.string.order_detail_payment_passbook), fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun OrderDetailPrice(modifier: Modifier, totalPrice: Int) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.order_detail_product_price), modifier = Modifier.weight(1f))
            Text(text = stringResource(R.string.order_detail_product_price_monetary, totalPrice))
        }
        // FIXME 배달비 측정 기준 필요
        Row(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.order_detail_delivery_price),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(OrderDetailPaymentBoxBackgroundColor)
                ) {
                    Text(
                        text = stringResource(R.string.order_detail_delivery_price_detail),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
            Text("22,000원")
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
        // FIXME 배달비 측정 기준 완료 시 총 금액구해야함
        Row() {
            Text(
                text = stringResource(R.string.order_detail_total_price),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text("24,000원", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun OrderDetailAgreeToTerms(modifier: Modifier) {
    val termsTitles = listOf(
        stringResource(R.string.order_detail_terms_conditions),
        stringResource(R.string.order_detail_terms_personal_information),
        stringResource(R.string.order_detail_terms_electronic_financial),
        stringResource(R.string.order_detail_terms_older),
        stringResource(R.string.order_detail_terms_third_parties),
        stringResource(R.string.order_detail_terms_third_parties),
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(false, {})
                Text(text = stringResource(R.string.order_detail_terms_all), fontWeight = FontWeight.Bold)
            }
            HorizontalDivider()
            for (term in termsTitles) {
                Row(modifier = Modifier.padding(end = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(false, {})
                    Text(term, Modifier.weight(1f), fontSize = 14.sp)
                    Text(
                        text = stringResource(R.string.order_detail_terms_view_content),
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline,
                        color = OrderDetailDeleteIconColor
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderDetailOrderButton(modifier: Modifier, menuCount: Int) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
        onClick = {}
    ) {
        // FIXME 배달비 측정 기준 완료 시 총 금액구해야함
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.order_detail_paying_btn_text, 1),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 4.dp)
            )
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("$menuCount", color = PointColor, modifier = Modifier.scale(0.9f))
            }
        }
    }
}
