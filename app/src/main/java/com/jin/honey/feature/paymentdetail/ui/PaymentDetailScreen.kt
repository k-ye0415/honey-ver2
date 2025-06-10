package com.jin.honey.feature.paymentdetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.jin.honey.feature.review.ui.fallbackData
import com.jin.honey.ui.theme.HoneyTheme
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
            Row {
                Text(text = paymentFallback.cart.firstOrNull()?.menuName.orEmpty())
                Icon(Icons.Default.ArrowForwardIos, contentDescription = "")
            }
            Row {
                CustomBoxButton(
                    modifier = Modifier.weight(1f),
                    rippleColor = PointColor,
                    borderColor = PointColor,
                    btnText = "재주문",
                    textColor = PointColor,
                    fontWeight = FontWeight.Normal,
                    onClickButton = {}
                )
                CustomBoxButton(
                    modifier = Modifier.weight(1f),
                    rippleColor = PointColor,
                    borderColor = PointColor,
                    btnText = "리뷰쓰기",
                    textColor = PointColor,
                    fontWeight = FontWeight.Normal,
                    onClickButton = {}
                )
            }
            HorizontalDivider()
            Text("주문자 정보")
            Row {
                Text("주소")
                Column {
                    Text("${paymentFallback.address.address.addressName.lotNumAddress} ${paymentFallback.address.addressDetail}")
                    Text("[도로명] ${paymentFallback.address.address.addressName.roadAddress} ${paymentFallback.address.addressDetail}")
                }
            }
            Row {
                Text("가게 요청사항")
                Text(paymentFallback.requirement.requirement)
            }
            Row {
                Text("라이더 요청사항")
                Text(paymentFallback.requirement.riderRequirement)
            }
            HorizontalDivider()
            Text("주문내역")
            for (menus in paymentFallback.cart) {
                Text(menus.menuName)
                for (ingredient in menus.ingredients) {
                    Row {
                        Text(ingredient.name)
                        Text("x ${ingredient.cartQuantity}")
                        Text("${(ingredient.cartQuantity * ingredient.unitPrice)}")
                    }
                }
            }
            Row {
                Text("상품금액")
                Text("${paymentFallback.prices.productPrice}")
            }
            HorizontalDivider()
            Row {
                Text("배달요금")
                Text("${paymentFallback.prices.deliveryPrice}")
            }
            HorizontalDivider()
            Row {
                Text("총 결제금액")
                Text("${paymentFallback.prices.totalPrice}")
            }
            HorizontalDivider()
            Text("주문정보")
            Row {
                Text("주문번호")
                Text("123904r9032")
            }
            Row {
                Text("주문시간")
                Text(paymentFallback.payInstant.toEpochMilli().toString())
            }
        }
    }
}

@Composable
fun CustomBoxButton(
    modifier: Modifier,
    rippleColor: Color,
    borderColor: Color,
    btnText: String,
    textColor: Color,
    fontWeight: FontWeight,
    onClickButton: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
            .indication(
                interactionSource,
                rememberRipple(color = rippleColor, bounded = true)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClickButton
            )
            .border(1.dp, borderColor, RoundedCornerShape(4.dp))
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(btnText, fontSize = 14.sp, color = textColor, fontWeight = fontWeight)
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
    id = null, payInstant = Instant.now(), payState = PaymentState.ORDER, address = UserAddress(
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
