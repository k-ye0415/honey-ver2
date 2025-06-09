package com.jin.honey.feature.order.ui

import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextOverflow
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
import com.jin.honey.feature.payment.domain.model.PayPrice
import com.jin.honey.feature.payment.domain.model.Payment
import com.jin.honey.feature.payment.domain.model.PaymentState
import com.jin.honey.feature.payment.domain.model.Requirement
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.OrderHistoryBoxBorderColor
import com.jin.honey.ui.theme.OrderHistoryDateTimeTextColor
import com.jin.honey.ui.theme.OrderHistoryListBackgroundColor
import com.jin.honey.ui.theme.PointColor
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun OrderScreen(viewModel: OrderViewModel, onNavigateToOrder: () -> Unit, onNavigateToCategory: () -> Unit) {
    val context = LocalContext.current
    val cartItemsState by viewModel.cartItemState.collectAsState()
    val orderHistoryListState by viewModel.orderHistoryListState.collectAsState()

    val cartItems = when (val state = cartItemsState) {
        is UiState.Success -> state.data
        else -> null
    }

//    val orderHistoryList = when(val state = orderHistoryListState) {
//
//    }

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
            onNavigateToOrder = onNavigateToOrder,
            onNavigateToCategory = onNavigateToCategory,
        )
        // order
        OrderHistoryScreen(orderHistoryListState)
    }

}

@Composable
fun OrderHistoryScreen(orderHistoryList: List<Payment>) {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_order_history),
                contentDescription = stringResource(R.string.order_history_icon_desc),
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )
            Text(stringResource(R.string.order_history_title), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        HorizontalDivider()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.background(OrderHistoryListBackgroundColor)
        ) {
            items(orderHistoryList.size) {
                val item = orderHistoryList[it]
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Row(modifier = Modifier.padding(bottom = 10.dp)) {
                        Text(
                            formatInstantToDataTime(item.payInstant),
                            color = OrderHistoryDateTimeTextColor,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            PaymentState.findByStateLabel(item.payState.state),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Row {
                        AsyncImage(
                            model = item.cart.firstOrNull()?.menuImageUrl.orEmpty(),
                            contentDescription = stringResource(R.string.order_history_menu_img_desc),
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.height(80.dp)) {
                            Text(item.cart.firstOrNull()?.menuName.orEmpty(), fontWeight = FontWeight.Bold)
                            Text(
                                item.cart.firstOrNull()?.ingredients?.firstOrNull()?.name.orEmpty(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.weight(1f))
                            Row {
                                CustomBoxButton(
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .weight(1f),
                                    rippleColor = PointColor,
                                    borderColor = PointColor,
                                    btnText = stringResource(R.string.order_history_reorder),
                                    textColor = PointColor,
                                    fontWeight = FontWeight.Bold,
                                    onClickButton = {}
                                )
                                CustomBoxButton(
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .weight(1f),
                                    rippleColor = Color.Gray,
                                    borderColor = OrderHistoryBoxBorderColor,
                                    btnText = stringResource(R.string.order_history_review),
                                    textColor = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    onClickButton = {}
                                )
                                CustomBoxButton(
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .weight(1f),
                                    rippleColor = Color.Gray,
                                    borderColor = OrderHistoryBoxBorderColor,
                                    btnText = stringResource(R.string.order_history_order_detail),
                                    textColor = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    onClickButton = {}
                                )
                            }
                        }
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
            .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(btnText, fontSize = 12.sp, color = textColor, fontWeight = fontWeight)
    }
}

private fun formatInstantToDataTime(instant: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd a HH:mm", Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
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
