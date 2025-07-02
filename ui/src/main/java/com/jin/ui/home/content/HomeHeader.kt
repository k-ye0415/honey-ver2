package com.jin.ui.home.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.ui.R
import com.jin.domain.address.model.Address

@Composable
fun HomeHeader(
    address:  Address?,
    onBottomSheetClose: (state: Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onBottomSheetClose(true) }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val addressLabel = address?.address?.addressName?.roadAddress ?: stringResource(R.string.order_detail_need_to_address)
        Text(
            text = addressLabel,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 4.dp)
        )
        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = "", modifier = Modifier.size(24.dp))
    }
}
