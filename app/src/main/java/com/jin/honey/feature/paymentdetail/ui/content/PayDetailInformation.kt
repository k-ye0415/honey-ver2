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
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PayDetailInformation(orderKey: String, orderInstant: Instant) {
    Column(modifier = Modifier
        .padding(horizontal = 10.dp)
        .padding(bottom = 20.dp)) {
        Text(
            text = stringResource(R.string.payment_detail_information),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        RowContent(title = stringResource(R.string.payment_detail_order_number), content = orderKey)
        RowContent(
            title = stringResource(R.string.payment_detail_order_date_timer),
            content = formatInstantToDataTime(orderInstant)
        )
    }
}

@Composable
private fun RowContent(title: String, content: String) {
    Row {
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

private fun formatInstantToDataTime(instant: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd a HH:mm", Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}
