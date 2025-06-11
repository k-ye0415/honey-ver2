package com.jin.honey.feature.paymentdetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PayDetailBoxBorderColor
import com.jin.honey.ui.theme.PayDetailDividerColor
import com.jin.honey.ui.theme.PayDetailRoadAddressColor
import com.jin.honey.ui.theme.PointColor
import java.time.Instant

@Composable
fun PaymentDetailScreen(paymentId: Int) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            IconButton({}) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.review_back_icon_desc),
                    tint = Color.Black
                )
            }
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

@Composable
@Preview(showBackground = true)
fun ReviewScreen12() {
    HoneyTheme {
        PaymentDetailScreen(1)
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
