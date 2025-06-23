package com.jin.honey.feature.orderdetail.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.address.domain.model.Address

@Composable
fun OrderAddress(addresses: List<Address>, modifier: Modifier, onChangedAddress: () -> Unit) {
    val currentAddress = addresses.find { it.isLatestAddress }
    val loadAddressLabel = generateLoadAddressLabel(currentAddress)
    val allAddressLabel = generateAllAddressLabel(currentAddress)
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onChangedAddress() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_current_location),
                contentDescription = stringResource(R.string.order_detail_address_icon_desc),
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(18.dp)
            )
            Text(
                text = loadAddressLabel,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = stringResource(R.string.order_detail_to_delivery), fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Icon(
                Icons.Default.ArrowForwardIos,
                contentDescription = stringResource(R.string.order_detail_change_address_icon_desc),
                modifier = Modifier.size(14.dp)
            )
        }
        Text(
            text = allAddressLabel,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 22.dp),
            lineHeight = 1.5.em
        )
    }
}

@Composable
private fun generateLoadAddressLabel(latestAddress: Address?): String = if (latestAddress != null) {
    latestAddress.address.addressName.roadAddress.ifEmpty { latestAddress.address.addressName.lotNumAddress }
} else {
    stringResource(R.string.order_detail_need_to_address)
}

@Composable
private fun generateAllAddressLabel(latestAddress: Address?): String = if (latestAddress != null) {
    "${latestAddress.address.addressName.roadAddress} ${latestAddress.addressDetail}"
} else {
    stringResource(R.string.order_detail_need_to_address_detail)
}
