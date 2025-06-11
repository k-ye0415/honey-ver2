package com.jin.honey.feature.paymentdetail.ui.content

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
fun PayDetailInformation() {
    Text(
        text = stringResource(R.string.payment_detail_information),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 8.dp)
    )
    RowContent(title = stringResource(R.string.payment_detail_order_number), content = paymentFallback.orderKey)
    RowContent(
        title = stringResource(R.string.payment_detail_order_date_timer),
        content = paymentFallback.payInstant.toEpochMilli().toString()
    )
}

@Composable
private fun RowContent(title: String, content: String) {
    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = title,
            fontSize = 12.sp
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = content,
            fontSize = 12.sp
        )
    }
}
