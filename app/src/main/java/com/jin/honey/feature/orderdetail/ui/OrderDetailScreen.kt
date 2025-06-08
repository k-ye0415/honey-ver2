package com.jin.honey.feature.orderdetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.UserAddress
import com.jin.honey.feature.home.ui.content.headercontent.LocationSearchBottomSheet
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.FoodSearchBoxBorderColor
import com.jin.honey.ui.theme.OrderDetailBoxBorderColor
import com.jin.honey.ui.theme.OrderDetailBoxDividerColor
import com.jin.honey.ui.theme.OrderDetailDeleteIconColor
import com.jin.honey.ui.theme.OrderDetailMenuClearTextColor
import com.jin.honey.ui.theme.OrderDetailPaymentBoxBackgroundColor
import com.jin.honey.ui.theme.OrderDetailRequirementCheckedColor
import com.jin.honey.ui.theme.OrderDetailRequirementHintColor
import com.jin.honey.ui.theme.PointColor

@Composable
fun OrderDetailScreen(
    viewModel: OrderDetailViewModel,
    cartItems: List<Cart>,
    onNavigateToLocationDetail: (address: Address) -> Unit
) {
    val latestAddressState by viewModel.latestAddressState.collectAsState()
    val addressSearchState by viewModel.addressSearchState.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var addressSearchKeyword by remember { mutableStateOf("") }

    val latestAddress = when (val state = latestAddressState) {
        is UiState.Success -> state.data
        else -> null
    }

    val addressSearchList = when (val state = addressSearchState) {
        is SearchState.Success -> state.data
        else -> emptyList()
    }

    LaunchedEffect(addressSearchKeyword) {
        viewModel.searchAddressByKeyword(addressSearchKeyword)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
        if (showBottomSheet) {
            LocationSearchBottomSheet(
                userAddresses = if (latestAddress != null) listOf(latestAddress) else emptyList(),
                keyword = addressSearchKeyword,
                addressSearchList = addressSearchList,
                onBottomSheetClose = { showBottomSheet = it },
                onLocationQueryChanged = { addressSearchKeyword = it },
                onNavigateToLocationDetail = onNavigateToLocationDetail
            )
        } else {
            addressSearchKeyword = ""
        }

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
                    onChangedAddress = { showBottomSheet = true }
                )
            }
            item {
                CartItems(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    cartItems = cartItems
                )
            }

            item {
                OrderDetailRequirements(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp)
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
    }
}

@Composable
private fun OrderDetailHeader() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        IconButton({}) {
            Icon(
                imageVector = Icons.Outlined.ArrowBackIosNew,
                contentDescription = stringResource(R.string.ingredient_back_icon_desc),
                tint = Color.Black
            )
        }
        Text(
            text = stringResource(R.string.order_detail_title),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun OrderAddress(latestAddress: UserAddress?, modifier: Modifier, onChangedAddress: () -> Unit) {
    val loadAddressLabel = latestAddress?.address?.addressName?.roadAddress ?: "주소 설정이 필요합니다."
    val allAddressLabel = if (latestAddress != null) {
        "${latestAddress.address.addressName.roadAddress} ${latestAddress.addressDetail}"
    } else {
        "상세 주소가 필요해요"
    }
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onChangedAddress() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_current_location),
                contentDescription = stringResource(R.string.order_detail_address_icon_desc),
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(18.dp)
            )
            Text(
                text = loadAddressLabel,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text("(으)로 배달", fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Icon(
                Icons.Default.ArrowForwardIos,
                contentDescription = stringResource(R.string.order_detail_change_address_icon_desc),
                modifier = Modifier.size(14.dp)
            )
        }
        Text(
            text = allAddressLabel,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 22.dp),
            lineHeight = 1.5.em
        )
    }
}

@Composable
private fun CartItems(modifier: Modifier, cartItems: List<Cart>) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
    ) {
        Column {
            for (item in cartItems) {
                Column {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp)) {
                        Text(item.menuName, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                        Text(
                            stringResource(R.string.order_detail_cart_menu_clear),
                            color = OrderDetailMenuClearTextColor,
                            fontSize = 12.sp
                        )
                    }
                    HorizontalDivider(color = OrderDetailBoxDividerColor)
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp)) {
                        AsyncImage(
                            model = item.menuImageUrl,
                            contentDescription = stringResource(R.string.order_detail_cart_menu_img_descr),
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .size(50.dp)
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                            for (ingredient in item.ingredients) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(ingredient.name, modifier = Modifier.weight(2f))
                                    Text(ingredient.quantity, modifier = Modifier.weight(1f))
                                    Text(
                                        ingredient.cartQuantity.toString(),
                                        modifier = Modifier.width(30.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = stringResource(R.string.order_detail_cart_ingredient_delete_icon_desc),
                                        modifier = Modifier.size(16.dp),
                                        tint = OrderDetailDeleteIconColor
                                    )
                                }
                            }
                        }
                    }
                }
            }
            // FIXME 기능 개발 시 수정
            val interactionSource = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .indication(
                            interactionSource = interactionSource,
                            indication = rememberRipple(
                                color = Color.Gray,
                                bounded = true,
                            )
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {}
                        )
                        .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.order_detail_cart_option_modify),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            HorizontalDivider(color = OrderDetailBoxDividerColor)
            Row(
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.order_detail_cart_add_icon_desc))
                Text(text = stringResource(R.string.order_detail_cart_add))
            }
        }
    }
}

@Composable
private fun OrderDetailRequirements(modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
    ) {
        var keyword = ""
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
                    value = keyword,
                    onValueChange = { keyword = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
//                            .focusRequester(focusRequester)
                        .onFocusChanged { },
                    decorationBox = { innerTextField ->
                        if (keyword.isEmpty()) {
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
