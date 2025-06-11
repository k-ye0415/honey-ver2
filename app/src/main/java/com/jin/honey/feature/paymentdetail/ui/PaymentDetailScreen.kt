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
import androidx.compose.ui.tooling.preview.Preview
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.AddressName
import com.jin.honey.feature.district.domain.model.AddressTag
import com.jin.honey.feature.district.domain.model.Coordinate
import com.jin.honey.feature.district.domain.model.UserAddress
import com.jin.honey.feature.payment.domain.model.PayPrice
import com.jin.honey.feature.payment.domain.model.Payment
import com.jin.honey.feature.payment.domain.model.PaymentState
import com.jin.honey.feature.payment.domain.model.Requirement
import com.jin.honey.feature.paymentdetail.ui.content.PayDetailInformation
import com.jin.honey.feature.paymentdetail.ui.content.PayDetailOrderContent
import com.jin.honey.feature.paymentdetail.ui.content.PayDetailOrderInfo
import com.jin.honey.feature.paymentdetail.ui.content.PayDetailOrderPrice
import com.jin.honey.feature.paymentdetail.ui.content.PayDetailOverView
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.HoneyTheme
import java.time.Instant

@Composable
fun PaymentDetailScreen(viewModel: PaymentDetailViewModel, orderKey: String) {
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
                        PayDetailOverView()
                    }
                    item {
                        PayDetailOrderInfo()
                    }
                    item {
                        PayDetailOrderContent()
                    }
                    item {
                        PayDetailOrderPrice()
                    }
                    item {
                        PayDetailInformation()
                    }
                }
            }
        }
    }
}

val paymentFallback = Payment(
    id = null,
    orderKey = "duamsuam",
    payInstant = Instant.now(), payState = PaymentState.ORDER, address = UserAddress(
        id = null,
        addressTag = AddressTag.CURRENT,
        address = Address(
            placeName = "Drew Hudson", addressName = AddressName(
                lotNumAddress = "suspendisse",
                roadAddress = "sem"
            ), coordinate = Coordinate(x = 22.23, y = 24.25)
        ),
        addressDetail = "atomorum"
    ), cart = listOf(
        Cart(
            id = null,
            addedCartInstant = Instant.now(),
            menuName = "Belinda Bolton",
            menuImageUrl = "https://duckduckgo.com/?q=molestiae",
            ingredients = listOf(
                IngredientCart(
                    name = "Marlin Kirby",
                    cartQuantity = 8427,
                    quantity = "hendrerit",
                    unitPrice = 1953
                )
            ),
            isOrdered = false
        )
    ), requirement = Requirement(
        requirement = "vivamus",
        riderRequirement = "instructior"
    ), prices = PayPrice(
        productPrice = 8318,
        deliveryPrice = 5268,
        totalPrice = 9406
    )
)
