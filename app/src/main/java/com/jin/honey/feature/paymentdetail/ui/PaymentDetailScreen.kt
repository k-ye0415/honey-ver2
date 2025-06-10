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
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(top = 16.dp, bottom = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = paymentFallback.cart.firstOrNull()?.menuName.orEmpty(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Icon(Icons.Default.ArrowForwardIos, contentDescription = "", modifier = Modifier.size(18.dp))
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomBoxButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp),
                            rippleColor = PointColor,
                            borderColor = PointColor,
                            btnText = "재주문",
                            textColor = PointColor,
                            fontWeight = FontWeight.Normal,
                            onClickButton = {}
                        )
                        CustomBoxButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp),
                            rippleColor = PointColor,
                            borderColor = PointColor,
                            btnText = "리뷰쓰기",
                            textColor = PointColor,
                            fontWeight = FontWeight.Normal,
                            onClickButton = {}
                        )
                    }
                    HorizontalDivider()
                }
                item {
                    Text(
                        text = "주문자 정보",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(top = 14.dp, bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("주소")
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                            Text(text = "${paymentFallback.address.address.addressName.lotNumAddress} ${paymentFallback.address.addressDetail}")
                            Text(
                                text = "[도로명] ${paymentFallback.address.address.addressName.roadAddress} ${paymentFallback.address.addressDetail}",
                                fontSize = 14.sp,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("가게 요청사항")
                        Text(paymentFallback.requirement.requirement, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("라이더 요청사항")
                        Text(
                            paymentFallback.requirement.riderRequirement,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
                }
                item {
                    Text(
                        "주문내역",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 8.dp)
                    )
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 14.dp)
                    ) {
                        for (menus in paymentFallback.cart) {
                            Text(menus.menuName)
                            for (ingredient in menus.ingredients) {
                                Row {
                                    Text("${ingredient.name} ${ingredient.quantity}")
                                    Text("x ${ingredient.cartQuantity}")
                                    Spacer(Modifier.weight(1f))
                                    Text("${(ingredient.cartQuantity * ingredient.unitPrice)}")
                                }
                            }
                        }
                    }

                }
                item {
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text("상품금액", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = stringResource(
                                R.string.order_detail_product_price_monetary,
                                paymentFallback.prices.productPrice
                            ),
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text("배달요금")
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = stringResource(
                                R.string.order_detail_product_price_monetary,
                                paymentFallback.prices.deliveryPrice
                            )
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text("총 결제금액", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = stringResource(
                                R.string.order_detail_product_price_monetary,
                                paymentFallback.prices.totalPrice
                            ),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
                }
                item {
                    Text(
                        "주문정보", fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 8.dp)
                    )
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text("주문번호", fontSize = 12.sp)
                        Spacer(Modifier.weight(1f))
                        Text("123904r9032", fontSize = 12.sp)
                    }
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text("주문시간", fontSize = 12.sp)
                        Text(paymentFallback.payInstant.toEpochMilli().toString(), fontSize = 12.sp)
                    }
                }
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
            .clip(RoundedCornerShape(8.dp))
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
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
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
