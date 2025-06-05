package com.jin.honey.feature.home.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.LocationSearching
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.ui.theme.HoneyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeHeader() {
    var showBottomSheet by remember { mutableStateOf(false) }
    var keyword by remember { mutableStateOf("") }
    Row(modifier = Modifier.clickable {
        showBottomSheet = true
    }) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DistrictSearchBottomSheet(
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
        BottomSheetContent(keyword, onDistrictQueryChanged, focusRequester, coroutineScope, modalState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetContent(
    keyword: String,
    onDistrictQueryChanged: (keyword: String) -> Unit,
    focusRequester: FocusRequester,
    coroutineScope: CoroutineScope,
    modalState: SheetState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 12.dp)
                .size(width = 40.dp, height = 4.dp)
                .background(Color.LightGray, RoundedCornerShape(2.dp))
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "주소 설정",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
        )

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
                    .onFocusChanged { focusState ->
                        coroutineScope.launch {
                            if (focusState.isFocused) {
                                modalState.expand()
                            }
                        }
                    },
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

        Row(modifier = Modifier.padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.LocationSearching, contentDescription = "", modifier = Modifier.size(18.dp))
            Text("현재 위치로 주소 찾기", fontSize = 18.sp)
        }

        HorizontalDivider(thickness = 8.dp)

        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Outlined.LocationOn, contentDescription = "")
            Column {
                Row {
                    Text("현재 위치를 표시해야함")
                    Text("현재")
                }
                Text("현재 위치의 상세 주소를 표시해야함")
            }
        }

        HorizontalDivider(thickness = 8.dp, modifier = Modifier.padding(vertical = 16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Default.Home, contentDescription = "")
            Text("집 추가")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun BottomSheetContentPreview() {
    HoneyTheme {
        val modalState = rememberModalBottomSheetState()
        val coroutineScope = rememberCoroutineScope()
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        BottomSheetContent(
            keyword = "",
            onDistrictQueryChanged = {},
            focusRequester = focusRequester,
            coroutineScope = coroutineScope,
            modalState = modalState
        )
    }
}
