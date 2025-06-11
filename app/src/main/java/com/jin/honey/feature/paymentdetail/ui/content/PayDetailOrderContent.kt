package com.jin.honey.feature.paymentdetail.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PayDetailOrderContent(cartItems: List<Cart>) {
    Text(
        text = stringResource(R.string.order_history_title),
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
        for (menus in cartItems) {
            Text(menus.menuName)
            for (ingredient in menus.ingredients) {
                Row {
                    Text("${ingredient.name} ${ingredient.quantity} ")
                    Text(
                        text = stringResource(
                            R.string.payment_detail_cart_quantity,
                            ingredient.cartQuantity
                        )
                    )
                    Spacer(Modifier.weight(1f))
                    Text(text = formatPriceLabel(ingredient.cartQuantity * ingredient.unitPrice))
                }
            }
        }
    }
}

@Composable
private fun formatPriceLabel(price: Int): String {
    val formattedPrice = remember(price) {
        NumberFormat.getNumberInstance(Locale.KOREA).format(price)
    }
    return stringResource(R.string.order_detail_product_price_monetary, formattedPrice)
}
