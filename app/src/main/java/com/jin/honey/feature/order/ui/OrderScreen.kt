package com.jin.honey.feature.order.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.order.ui.content.cart.CartScreen
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.feature.ui.state.UiState

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

    Column() {
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
        Text(stringResource(R.string.order_history_title))
        HorizontalDivider()
        // FIXME : 주문내역 정의 후 UI 수정
        LazyColumn {
            items(orderFallback.size) {
                Text(orderFallback[it])
            }
        }
    }

}

val orderFallback = listOf("주문내역", "주문내역", "주문내역", "주문내역", "주문내역", "주문내역")
