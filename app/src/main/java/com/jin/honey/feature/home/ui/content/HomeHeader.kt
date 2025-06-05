package com.jin.honey.feature.home.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.feature.home.ui.content.headercontent.DistrictSearchBottomSheet
import com.jin.honey.ui.theme.HoneyTheme

@Composable
fun HomeHeader() {
    var showBottomSheet by remember { mutableStateOf(false) }
    var keyword by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .clickable { showBottomSheet = true }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("주소가 필요해요", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 4.dp))
        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = "", modifier = Modifier.size(24.dp))
    }

    if (showBottomSheet) {
        DistrictSearchBottomSheet(
            keyword = keyword,
            onBottomSheetClose = { showBottomSheet = it },
            onDistrictQueryChanged = { keyword = it })
    }
}

@Composable
@Preview(showBackground = true)
fun HomeHeaderPreview() {
    HoneyTheme {
        HomeHeader()
    }
}
