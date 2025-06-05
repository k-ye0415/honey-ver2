package com.jin.honey.feature.order.ui.content.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.CartKey
import com.jin.honey.feature.order.ui.content.cart.content.CartContent
import com.jin.honey.feature.order.ui.content.cart.content.CartHeader
import com.jin.honey.feature.order.ui.content.cart.content.CartOptionModifyBottomSheet

@Composable
fun CartScreen(
    cartItems: List<Cart>?,
    onRemoveCart: (cartItem: Cart, ingredientName: String) -> Unit,
    onChangeOption: (quantityMap: Map<CartKey, Int>) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    // cart
    CartHeader()
    // cart content
    if (cartItems.isNullOrEmpty()) {
        // FIXME : Cart item 없는 경우에 대한 UI 처리
    } else {
        if (showBottomSheet) {
            CartOptionModifyBottomSheet(
                cartItems,
                onRemoveCart = onRemoveCart,
                onBottomSheetClose = { showBottomSheet = it },
                onChangeOption = onChangeOption
            )
        }
        CartContent(cartItems) { showBottomSheet = it }
    }
}
