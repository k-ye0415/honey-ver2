package com.jin.honey.feature.paymentdetail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.jin.feature.ui.state.UiState
import com.jin.honey.R
import com.jin.honey.feature.paymentdetail.ui.content.PayDetailInformation
import com.jin.honey.feature.paymentdetail.ui.content.PayDetailOrderContent
import com.jin.honey.feature.paymentdetail.ui.content.PayDetailOrderInfo
import com.jin.honey.feature.paymentdetail.ui.content.PayDetailOrderPrice
import com.jin.honey.feature.paymentdetail.ui.content.PayDetailOverView
import com.jin.model.cart.Cart

@Composable
fun PaymentDetailScreen(
    viewModel: PaymentDetailViewModel,
    orderKey: String,
    onNavigateToIngredient: (menuName: String) -> Unit
) {
    val orderDetailState by viewModel.orderDetailState.collectAsState()
    LaunchedEffect(orderKey) {
        viewModel.fetchOrderDetail(orderKey)
    }

    val orderDetail = when (val state = orderDetailState) {
        is UiState.Success -> state.data
        else -> null
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            IconButton({}) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.review_back_icon_desc),
                    tint = Color.Black
                )
            }
            if (orderDetail == null) {
                // FIXME UI
            } else {
                LazyColumn {
                    item {
                        val menuName = generatedMenuNameLabel(orderDetail.cart)
                        PayDetailOverView(menuName = menuName)
                    }
                    item {
                        PayDetailOrderInfo(
                            lotNumAddress = orderDetail.address.address.addressName.lotNumAddress,
                            roadAddress = orderDetail.address.address.addressName.roadAddress,
                            addressDetail = orderDetail.address.addressDetail,
                            requirement = orderDetail.requirement.requirement,
                            riderRequirement = orderDetail.requirement.riderRequirement
                        )
                    }
                    item {
                        PayDetailOrderContent(
                            cartItems = orderDetail.cart,
                            onNavigateToIngredient = { menuName -> onNavigateToIngredient(menuName) }
                        )
                    }
                    item {
                        PayDetailOrderPrice(
                            productPrice = orderDetail.prices.productPrice,
                            deliveryPrice = orderDetail.prices.deliveryPrice,
                            totalPrice = orderDetail.prices.totalPrice
                        )
                    }
                    item {
                        PayDetailInformation(orderKey = orderKey, orderInstant = orderDetail.payInstant)
                    }
                }
            }
        }
    }
}

private fun generatedMenuNameLabel(cartItems: List<Cart>): String {
    return if (cartItems.size > 1) {
        "${cartItems.firstOrNull()?.menuName.orEmpty()} 외 ${cartItems.size - 1}"
    } else {
        cartItems.firstOrNull()?.menuName.orEmpty()
    }
}
