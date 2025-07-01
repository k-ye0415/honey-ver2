package com.jin.honey.feature.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.cart.ui.content.CartContent
import com.jin.honey.feature.cart.ui.content.CartHeader
import com.jin.honey.feature.cart.ui.content.CartOptionModifyBottomSheet
import com.jin.domain.model.cart.Cart
import com.jin.model2.cart.CartKey
import com.jin.ui.theme.OrderDetailBoxBorderColor

@Composable
fun CartScreen(
    cartItems: List<Cart>?,
    onRemoveCart: (cartItem: Cart, ingredientName: String) -> Unit,
    onChangeOption: (quantityMap: Map<CartKey, Int>) -> Unit,
    onNavigateToOrder: () -> Unit,
    onNavigateToCategory: () -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    // cart
    CartHeader()
    // cart content
    if (cartItems.isNullOrEmpty()) {
        EmptyCartItem(onNavigateToCategory)
    } else {
        if (showBottomSheet) {
            CartOptionModifyBottomSheet(
                cartItems,
                onRemoveCart = onRemoveCart,
                onBottomSheetClose = { showBottomSheet = it },
                onChangeOption = onChangeOption
            )
        }
        CartContent(
            cartItems = cartItems,
            onBottomSheetClose = { showBottomSheet = it },
            onNavigateToOrder = onNavigateToOrder
        )
    }
}

@Composable
fun EmptyCartItem(onNavigateToCategory: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.order_cart_empty),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 14.dp)
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .indication(interactionSource, rememberRipple(color = Color.Gray, bounded = true))
                .clickable(interactionSource = interactionSource, indication = null, onClick = onNavigateToCategory)
                .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
                .padding(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Text(text = stringResource(R.string.order_cart_look_around_menu), fontWeight = FontWeight.Bold)
        }
    }
}
