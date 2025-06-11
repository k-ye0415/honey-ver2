package com.jin.honey.feature.paymentdetail.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.paymentdetail.ui.paymentFallback

@Composable
fun PayDetailOrderContent(){
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
        for (menus in paymentFallback.cart) {
            Text(menus.menuName)
            for (ingredient in menus.ingredients) {
                Row {
                    Text("${ingredient.name} ${ingredient.quantity}")
                    Text(
                        text = stringResource(
                            R.string.payment_detail_cart_quantity,
                            ingredient.cartQuantity
                        )
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = stringResource(
                            R.string.order_detail_product_price_monetary,
                            (ingredient.cartQuantity * ingredient.unitPrice)
                        )
                    )
                }
            }
        }
    }
}
