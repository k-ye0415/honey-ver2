package com.jin.ui.payment.content

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jin.ui.R
import com.jin.ui.theme.PayDetailDividerColor
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PayDetailOrderPrice(productPrice: Int, deliveryPrice: Int, totalPrice: Int) {
    RowContent(
        title = stringResource(R.string.order_detail_product_price),
        price = productPrice,
        fontWeight = FontWeight.SemiBold
    )
    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = PayDetailDividerColor)
    RowContent(
        title = stringResource(R.string.order_detail_delivery_price),
        price = deliveryPrice,
        fontWeight = FontWeight.Normal
    )
    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = PayDetailDividerColor)
    RowContent(
        title = stringResource(R.string.order_detail_total_price),
        price = totalPrice,
        fontWeight = FontWeight.Bold
    )
    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = PayDetailDividerColor)
}

@Composable
private fun RowContent(title: String, price: Int, fontWeight: FontWeight) {
    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = title,
            fontWeight = fontWeight
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = formatPriceLabel(price),
            fontWeight = fontWeight
        )
    }
}

@Composable
private fun formatPriceLabel(price: Int): String {
    val formattedPrice = remember(price) {
        NumberFormat.getNumberInstance(Locale.KOREA).format(price)
    }
    return stringResource(R.string.order_detail_product_price_monetary, formattedPrice)
}
