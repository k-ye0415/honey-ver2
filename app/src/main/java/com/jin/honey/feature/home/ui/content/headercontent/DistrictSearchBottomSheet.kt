package com.jin.honey.feature.home.ui.content.headercontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.ui.theme.PointColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistrictSearchBottomSheet(
    keyword: String,
    onBottomSheetClose: (state: Boolean) -> Unit,
    onDistrictQueryChanged: (keyword: String) -> Unit
) {
    val modalState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    ModalBottomSheet(
        sheetState = modalState,
        onDismissRequest = { onBottomSheetClose(false) },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BottomSheetHeader(Modifier.align(Alignment.CenterHorizontally))
            BottomSheetDistrictSearchBox(
                keyword = keyword,
                focusRequester = focusRequester,
                onDistrictQueryChanged = onDistrictQueryChanged,
                onFocusChanged = { isFocused ->
                    coroutineScope.launch {
                        if (isFocused) {
                            modalState.expand()
                        }
                    }
                })
            CurrentLocationSearch()
            CurrentDistrict()
            AddHome()
        }
    }
}

@Composable
private fun BottomSheetHeader(modifier: Modifier) {
    Box(
        modifier = modifier
            .padding(top = 16.dp, bottom = 12.dp)
            .size(width = 40.dp, height = 4.dp)
            .background(Color.LightGray, RoundedCornerShape(2.dp))
    )

    Text(
        text = "주소 설정",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
    )
}

@Composable
private fun BottomSheetDistrictSearchBox(
    keyword: String,
    focusRequester: FocusRequester,
    onDistrictQueryChanged: (keyword: String) -> Unit,
    onFocusChanged: (isFocused: Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(0xFFf6f6f6), RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "",
            tint = Color(0xff949494),
            modifier = Modifier.padding(end = 8.dp)
        )

        BasicTextField(
            value = keyword,
            onValueChange = onDistrictQueryChanged,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { onFocusChanged(it.isFocused) },
            decorationBox = { innerTextField ->
                if (keyword.isEmpty()) {
                    Text(
                        text = "건물명, 도로명 또는 지번으로 검색",
                        color = Color(0xffacacac),
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun CurrentLocationSearch() {
    Row(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_location_search),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 4.dp)
                .size(18.dp)
        )
        Text("현재 위치로 주소 찾기", fontSize = 18.sp)
    }

    HorizontalDivider(thickness = 1.dp, color = Color(0xFFe5e5e5))
    HorizontalDivider(thickness = 8.dp, color = Color(0xFFf2f2f2))
}

@Composable
private fun CurrentDistrict() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_current_location),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(18.dp)
            )
            Text("현재 위치를 표시해야함", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFfff5f8))
            ) {
                Text(
                    "현재",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = PointColor,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            }
        }
        Text(
            "현재 위치의 상세 주소를 표시해야함",
            fontSize = 12.sp,
            color = Color(0xFFababab),
            modifier = Modifier.padding(start = 22.dp)
        )
    }

    HorizontalDivider(thickness = 1.dp, color = Color(0xFFe5e5e5))
    HorizontalDivider(thickness = 8.dp, color = Color(0xFFf2f2f2))
}

@Composable
private fun AddHome() {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_home),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 4.dp)
                .size(20.dp)
        )
        Text("집 추가", fontWeight = FontWeight.Bold)
    }
}
