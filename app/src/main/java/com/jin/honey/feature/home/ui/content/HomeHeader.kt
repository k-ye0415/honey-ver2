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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.feature.district.domain.model.District
import com.jin.honey.feature.district.domain.model.UserDistrict
import com.jin.honey.feature.home.ui.content.headercontent.DistrictSearchBottomSheet

@Composable
fun HomeHeader(
    districtList: List<UserDistrict>,
    keyword: String,
    districtSearchList: List<District>,
    onDistrictQueryChanged: (keyword: String) -> Unit,
    onNavigateToDistrictDetail: (district: District) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(districtList) {
        showBottomSheet = districtList.isEmpty()
    }

    Row(
        modifier = Modifier
            .clickable { showBottomSheet = true }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // FIXME 앱 첫 실행할때 받아올 수 있도록 수정 필요
        Text(
            text = if (districtList.isEmpty()) "주소가 필요해요" else districtList.firstOrNull()?.district?.address?.roadAddress
                ?: "주소가 필요해요",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 4.dp)
        )
        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = "", modifier = Modifier.size(24.dp))
    }

    if (showBottomSheet) {
        DistrictSearchBottomSheet(
            districtList = districtList,
            keyword = keyword,
            districtSearchList = districtSearchList,
            onBottomSheetClose = { showBottomSheet = it },
            onDistrictQueryChanged = onDistrictQueryChanged,
            onNavigateToDistrictDetail = onNavigateToDistrictDetail
        )
    } else {
        onDistrictQueryChanged("")
    }
}
