package com.jin.honey.feature.orderdetail.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R

@Composable
fun OrderDetailPrice(modifier: Modifier, productPrice: String, ridePrice: String, totalPrice: String) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.order_detail_product_price), modifier = Modifier.weight(1f))
            Text(text = productPrice)
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.order_detail_delivery_price),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            )
            Text(text = ridePrice)
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
        Row {
            Text(
                text = stringResource(R.string.order_detail_total_price),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = totalPrice,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
