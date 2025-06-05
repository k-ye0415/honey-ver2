package com.jin.honey.feature.home.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jin.honey.feature.home.ui.content.headercontent.DistrictSearchBottomSheet

@Composable
fun HomeHeader() {
    var showBottomSheet by remember { mutableStateOf(false) }
    var keyword by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.clickable { showBottomSheet = true }
    ) {
        Text("주소가 필요해요")
        Icon(Icons.Default.ArrowDropDown, contentDescription = "")
    }

    if (showBottomSheet) {
        DistrictSearchBottomSheet(
            keyword = keyword,
            onBottomSheetClose = { showBottomSheet = it },
            onDistrictQueryChanged = { keyword = it })
    }
}
