package com.jin.honey.feature.home.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.address.domain.model.SearchAddress
import com.jin.honey.feature.address.domain.model.Address
import com.jin.honey.feature.home.ui.content.headercontent.LocationSearchBottomSheet

@Composable
fun HomeHeader(
    addresses: List<Address>,
    keyword: String,
    searchAddressSearchList: List<SearchAddress>,
    onAddressQueryChanged: (keyword: String) -> Unit,
    onNavigateToAddressDetail: (searchAddress: SearchAddress) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(addresses) {
        showBottomSheet = addresses.isEmpty()
    }

    Row(
        modifier = Modifier
            .clickable { showBottomSheet = true }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val addressLabel = if (addresses.isEmpty()) {
            stringResource(R.string.order_detail_need_to_address)
        } else {
            addresses.firstOrNull()?.address?.addressName?.roadAddress
                ?: stringResource(R.string.order_detail_need_to_address)
        }
        Text(
            text = addressLabel,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 4.dp)
        )
        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = "", modifier = Modifier.size(24.dp))
    }

    if (showBottomSheet) {
        LocationSearchBottomSheet(
            addresses = addresses,
            keyword = keyword,
            searchAddressSearchList = searchAddressSearchList,
            onBottomSheetClose = { showBottomSheet = it },
            onAddressQueryChanged = onAddressQueryChanged,
            onNavigateToLocationDetail = onNavigateToAddressDetail
        )
    } else {
        onAddressQueryChanged("")
    }
}
