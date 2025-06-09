package com.jin.honey.feature.order.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.AddressName
import com.jin.honey.feature.district.domain.model.AddressTag
import com.jin.honey.feature.district.domain.model.Coordinate
import com.jin.honey.feature.district.domain.model.UserAddress
import com.jin.honey.feature.order.ui.content.cart.CartScreen
import com.jin.honey.feature.payment.domain.PayPrice
import com.jin.honey.feature.payment.domain.Payment
import com.jin.honey.feature.payment.domain.PaymentState
import com.jin.honey.feature.payment.domain.Requirement
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.HoneyTheme
import java.time.Instant

@Composable
fun OrderScreen(viewModel: OrderViewModel, onNavigateToOrder: () -> Unit) {
    val context = LocalContext.current
    val cartItemsState by viewModel.cartItemState.collectAsState()

    val cartItems = when (val state = cartItemsState) {
        is UiState.Success -> state.data
        else -> null
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

    Column {
        // title
        Text(
            text = stringResource(R.string.order_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        CartScreen(
            cartItems,
            onRemoveCart = { cartItem, ingredientName -> viewModel.removeCartItem(cartItem, ingredientName) },
            onChangeOption = { viewModel.modifyCartQuantity(it) },
            onNavigateToOrder = onNavigateToOrder
        )
        // order

    }

}

@Composable
fun OrderHistoryScreen() {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_order_history),
                contentDescription = stringResource(R.string.order_cart_icon_desc),
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )
            Text(stringResource(R.string.order_history_title), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        HorizontalDivider()
        LazyColumn {
            items(orderFallback.size) {
                val item = orderFallback[it]
                Column {
                    Row {
                        Text(item.payInstant.toEpochMilli().toString())
                        Text(item.payState.state)
                    }
                    Row {
                        AsyncImage(
                            model = "",
                            contentDescription = "",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Column {
                            Text(item.cart.firstOrNull()?.menuName.orEmpty())
                            Text(item.cart.firstOrNull()?.ingredients?.firstOrNull()?.name.orEmpty())
                            Row {
                                Box(modifier = Modifier.weight(1f)) {
                                    Text("재주문")
                                }
                                Box(modifier = Modifier.weight(1f)) {
                                    Text("리뷰쓰기")
                                }
                                Box(modifier = Modifier.weight(1f)) {
                                    Text("주문상세")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    HoneyTheme {
        OrderHistoryScreen()
    }
}

val payment = Payment(
    id = null, payInstant = Instant.now(), payState = PaymentState.ORDER, address = UserAddress(
        id = null,
        addressTag = AddressTag.CURRENT,
        address = Address(
            placeName = "Georgette Rocha", addressName = AddressName(
                lotNumAddress = "theophrastus",
                roadAddress = "delectus"
            ), coordinate = Coordinate(x = 8.9, y = 10.11)
        ),
        addressDetail = "discere"
    ), cart = listOf(
        Cart(
            id = null,
            addedCartInstant = Instant.now(),
            menuName = "Hazel Brady",
            menuImageUrl = "https://www.google.com/#q=dicit",
            ingredients = listOf(
                IngredientCart(
                    name = "James Cain",
                    cartQuantity = 2613,
                    quantity = "aliquet",
                    unitPrice = 4237
                )
            ),
            isOrdered = false
        )
    ), requirement = Requirement(
        requirement = "vestibulum",
        riderRequirement = "viris"
    ), prices = PayPrice(
        productPrice = 8213,
        deliveryPrice = 8045,
        totalPrice = 8848
    )
)
val orderFallback = listOf(payment, payment, payment, payment)
