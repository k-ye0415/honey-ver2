package com.jin.ui.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.RoundedBoxButton
import com.jin.domain.cart.model.Cart
import com.jin.domain.cart.model.CartKey
import com.jin.ui.R
import com.jin.ui.cart.content.CartContent
import com.jin.ui.cart.content.CartHeader
import com.jin.ui.cart.content.CartOptionModifyBottomSheet
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
        RoundedBoxButton(
            modifier = Modifier,
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White,
            borderColor = OrderDetailBoxBorderColor,
            rippleColor = Color.Gray,
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 10.dp),
            onClick = onNavigateToCategory
        ) {
            Text(text = stringResource(R.string.order_cart_look_around_menu), fontWeight = FontWeight.Bold)
        }
    }
}
