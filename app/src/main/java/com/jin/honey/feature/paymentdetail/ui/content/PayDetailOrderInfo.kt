package com.jin.honey.feature.paymentdetail.ui.content

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.ui.theme.PayDetailDividerColor
import com.jin.honey.ui.theme.PayDetailRoadAddressColor

@Composable
fun PayDetailOrderInfo(
    lotNumAddress: String,
    roadAddress: String,
    addressDetail: String,
    requirement: String,
    riderRequirement: String
) {
    Text(
        text = stringResource(R.string.payment_detail_order_information),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 14.dp, bottom = 8.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.payment_detail_address))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            Text(text = "$lotNumAddress $addressDetail")
            Text(
                text = stringResource(R.string.payment_detail_road_address, roadAddress, addressDetail),
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                color = PayDetailRoadAddressColor
            )
        }
    }
    RowContent(stringResource(R.string.order_detail_requirements), requirement)
    RowContent(stringResource(R.string.order_detail_rider_requirements), riderRequirement)
    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = PayDetailDividerColor)
}

@Composable
private fun RowContent(title: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Text(
            text = content,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}
