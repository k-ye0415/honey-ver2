package com.jin.honey.feature.orderdetail.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jin.honey.R
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.home.ui.content.headercontent.LocationSearchBottomSheet
import com.jin.honey.feature.cart.ui.content.CartOptionModifyBottomSheet
import com.jin.honey.feature.orderdetail.ui.content.NeedAgreeToTermsDialog
import com.jin.honey.feature.orderdetail.ui.content.OrderAddress
import com.jin.honey.feature.orderdetail.ui.content.OrderDetailAgreeToTerms
import com.jin.honey.feature.orderdetail.ui.content.OrderDetailCartItems
import com.jin.honey.feature.orderdetail.ui.content.OrderDetailHeader
import com.jin.honey.feature.orderdetail.ui.content.OrderDetailPayment
import com.jin.honey.feature.orderdetail.ui.content.OrderDetailPrice
import com.jin.honey.feature.orderdetail.ui.content.OrderDetailRequirements
import com.jin.honey.feature.orderdetail.ui.content.RiderRequirementBottomSheet
import com.jin.honey.feature.order.domain.model.PayPrice
import com.jin.honey.feature.order.domain.model.Order
import com.jin.honey.feature.order.domain.model.PaymentState
import com.jin.honey.feature.order.domain.model.Requirement
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.SearchState
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.PointColor
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random

@Composable
fun OrderDetailScreen(
    viewModel: OrderDetailViewModel,
    onNavigateToLocationDetail: (address: Address) -> Unit,
    onNavigateToOrder: () -> Unit
) {
    val context = LocalContext.current
    val latestAddressState by viewModel.latestAddressState.collectAsState()
    val addressSearchState by viewModel.addressSearchState.collectAsState()
    val cartItemsState by viewModel.cartItemState.collectAsState()

    var showAddressBottomSheet by remember { mutableStateOf(false) }
    var showOptionModifyBottomSheet by remember { mutableStateOf(false) }
    var showRiderRequirementBottomSheet by remember { mutableStateOf(false) }
    var showAgreeToTermsDialog by remember { mutableStateOf(false) }

    var addressSearchKeyword by remember { mutableStateOf("") }
    var requirementsContent by remember { mutableStateOf("") }
    var requirementsChecked by remember { mutableStateOf(true) }
    var riderRequire by remember { mutableStateOf("") }
    var riderRequirementsContent by remember { mutableStateOf("") }
    var termsSelectedMap by remember {
        mutableStateOf(
            mapOf(
                context.getString(R.string.order_detail_terms_conditions) to false,
                context.getString(R.string.order_detail_terms_personal_information) to false,
                context.getString(R.string.order_detail_terms_electronic_financial) to false,
                context.getString(R.string.order_detail_terms_older) to false,
                context.getString(R.string.order_detail_terms_third_parties) to false
            )
        )
    }
    val allTermsSelected by remember(termsSelectedMap) { derivedStateOf { termsSelectedMap.values.all { it } } }

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

    val deliveryPrice = 2500
    val deliveryPriceLabel = formatPriceLabel(deliveryPrice)

    val productPrice = cartItems
        .flatMap { it.ingredients }
        .sumOf { it.unitPrice * it.cartQuantity }
    val productPriceLabel = formatPriceLabel(productPrice)
    val totalPriceLabel = formatPriceLabel(deliveryPrice + productPrice)

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

    LaunchedEffect(Unit) {
        viewModel.insertState.collect {
            when (it) {
                is DbState.Success -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.order_detail_toast_save_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    onNavigateToOrder()
                }

                is DbState.Error -> Toast.makeText(
                    context,
                    context.getString(R.string.order_detail_toast_save_error),
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
                    riderRequire = riderRequire,
                    riderContent = riderRequirementsContent,
                    checked = requirementsChecked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    onContentChanged = { requirementsContent = it },
                    onRiderContentChanged = { riderRequirementsContent = it },
                    onCheckedChanged = { requirementsChecked = it },
                    onShowRiderRequirement = { showRiderRequirementBottomSheet = true }
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
                OrderDetailPrice(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    productPrice = productPriceLabel,
                    ridePrice = deliveryPriceLabel,
                    totalPrice = totalPriceLabel
                )
            }
            item {
                OrderDetailAgreeToTerms(
                    isAllAgree = allTermsSelected,
                    termsMap = termsSelectedMap,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    onAllAgreeChecked = { checked ->
                        termsSelectedMap = termsSelectedMap.mapValues { checked }
                    },
                    onAgreeChecked = { term, checked ->
                        termsSelectedMap = termsSelectedMap.toMutableMap().apply { this[term] = checked }
                    }
                )
            }
            item {
                OrderDetailOrderButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    totalPrice = totalPriceLabel,
                    menuCount = cartItems.count(),
                    onClickOrder = {
                        if (termsSelectedMap.values.all { it } == false) {
                            showAgreeToTermsDialog = true
                        } else {
                            val order = Order(
                                id = null,
                                orderKey = generateOrderKey(),
                                payInstant = Instant.now(),
                                payState = PaymentState.ORDER,
                                address = latestAddress!!,
                                cart = cartItems,
                                requirement = Requirement(
                                    requirement = requirementsContent,
                                    riderRequirement = riderRequirementsContent
                                ),
                                prices = PayPrice(
                                    productPrice = productPrice,
                                    deliveryPrice = deliveryPrice,
                                    totalPrice = (productPrice + deliveryPrice)
                                )
                            )
                            viewModel.saveAfterPayment(order)
                        }
                    }
                )
            }
        }
        if (showAddressBottomSheet) {
            LocationSearchBottomSheet(
                userAddresses = if (latestAddress != null) listOf(latestAddress) else emptyList(),
                keyword = addressSearchKeyword,
                addressSearchList = addressSearchList,
                onBottomSheetClose = { showAddressBottomSheet = it },
                onAddressQueryChanged = { addressSearchKeyword = it },
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

        if (showRiderRequirementBottomSheet) {
            RiderRequirementBottomSheet(
                riderRequirementContent = riderRequire,
                onShowBottomSheet = { showRiderRequirementBottomSheet = it },
                onSelectedRiderRequire = { riderRequire = it }
            )
        }

        if (showAgreeToTermsDialog) {
            NeedAgreeToTermsDialog(onShowDialog = { showAgreeToTermsDialog = false })
        }
    }
}

@Composable
private fun OrderDetailOrderButton(modifier: Modifier, totalPrice: String, menuCount: Int, onClickOrder: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
        onClick = onClickOrder
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.order_detail_paying_btn_text, totalPrice),
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

@Composable
private fun formatPriceLabel(price: Int): String {
    val formattedPrice = remember(price) {
        NumberFormat.getNumberInstance(Locale.KOREA).format(price)
    }
    return stringResource(R.string.order_detail_product_price_monetary, formattedPrice)
}

private fun generateOrderKey(): String {
    val currentDate = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("yyMMdd")
    val datePart = currentDate.format(dateFormatter)

    val charPool: List<Char> = ('A'..'Z') + ('0'..'9')
    val randomPart = (1..8)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
    return "O$datePart-$randomPart"
}
