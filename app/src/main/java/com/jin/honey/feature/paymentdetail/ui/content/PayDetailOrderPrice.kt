package com.jin.honey.feature.paymentdetail.ui.content

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jin.honey.R
import com.jin.honey.feature.paymentdetail.ui.paymentFallback
import com.jin.honey.ui.theme.PayDetailDividerColor

@Composable
fun PayDetailOrderPrice() {
    RowContent(
        title = stringResource(R.string.order_detail_product_price),
        content = stringResource(
            R.string.order_detail_product_price_monetary,
            paymentFallback.prices.productPrice
        ),
        fontWeight = FontWeight.SemiBold
    )
    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = PayDetailDividerColor)
    RowContent(
        title = stringResource(R.string.order_detail_delivery_price),
        content = stringResource(
            R.string.order_detail_product_price_monetary,
            paymentFallback.prices.deliveryPrice
        ),
        fontWeight = FontWeight.Normal
    )
    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = PayDetailDividerColor)
    RowContent(
        title = stringResource(R.string.order_detail_total_price),
        content = stringResource(
            R.string.order_detail_product_price_monetary,
            paymentFallback.prices.totalPrice
        ),
        fontWeight = FontWeight.Bold
    )
    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = PayDetailDividerColor)
}

@Composable
private fun RowContent(title: String, content: String, fontWeight: FontWeight) {
    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = title,
            fontWeight = fontWeight
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = content,
            fontWeight = fontWeight
        )
    }
}
