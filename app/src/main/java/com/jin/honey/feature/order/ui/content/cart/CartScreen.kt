package com.jin.honey.feature.order.ui.content.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jin.honey.feature.order.ui.content.cart.content.CartContent
import com.jin.honey.feature.order.ui.content.cart.content.CartHeader
import com.jin.honey.feature.order.ui.content.cart.content.CartOptionModifyBottomSheet

@Composable
fun CartScreen() {
    var showBottomSheet by remember { mutableStateOf(false) }
    if (showBottomSheet) {
        CartOptionModifyBottomSheet { showBottomSheet = it }
    }
    // cart
    CartHeader()
    // cart content
    CartContent { showBottomSheet = it }
}
