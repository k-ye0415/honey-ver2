package com.jin.honey.feature.orderdetail.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jin.honey.feature.cart.domain.model.Cart

@Composable
fun OrderDetailScreen(cartItems: List<Cart>) {
    Text(cartItems.toString())
}
