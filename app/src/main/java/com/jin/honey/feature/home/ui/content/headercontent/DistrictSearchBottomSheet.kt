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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.district.domain.model.District
import com.jin.honey.ui.theme.CurrentDistrictBoxBackgroundColor
import com.jin.honey.ui.theme.DistrictSearchBoxBackgroundColor
import com.jin.honey.ui.theme.DistrictSearchHintTextColor
import com.jin.honey.ui.theme.DistrictSearchIconColor
import com.jin.honey.ui.theme.HorizontalDividerColor
import com.jin.honey.ui.theme.HorizontalDividerShadowColor
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.SearchDistrictDescriptionSummaryTextColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistrictSearchBottomSheet(
    keyword: String,
    districtSearchList: List<District>,
    onBottomSheetClose: (state: Boolean) -> Unit,
    onDistrictQueryChanged: (keyword: String) -> Unit
) {
    val modalState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    var isSearchFocused by remember { mutableStateOf(false) }

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
                    isSearchFocused = isFocused
                    coroutineScope.launch {
                        if (isFocused) {
                            modalState.expand()
                        }
                    }
                })
            when {
                isSearchFocused && keyword.isEmpty() -> {
                    CurrentLocationSearch()
                    SearchDescription()
                }

                isSearchFocused && keyword.isNotEmpty() -> {
                    SearchResultList(districtSearchList)
                }

                else -> {
                    CurrentLocationSearch()
                    CurrentDistrict()
                    AddHome()
                }
            }
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
        text = stringResource(R.string.district_title),
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
            .background(DistrictSearchBoxBackgroundColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.district_search_icon_desc),
            tint = DistrictSearchIconColor,
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
                        text = stringResource(R.string.district_search_hint),
                        color = DistrictSearchHintTextColor,
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
            contentDescription = stringResource(R.string.district_current_search_icon_desc),
            modifier = Modifier
                .padding(end = 4.dp)
                .size(18.dp)
        )
        Text(text = stringResource(R.string.district_current_search), fontSize = 18.sp)
    }

    HorizontalDivider(thickness = 1.dp, color = HorizontalDividerShadowColor)
    HorizontalDivider(thickness = 8.dp, color = HorizontalDividerColor)
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
                contentDescription = stringResource(R.string.district_current_icon_desc),
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(18.dp)
            )
            Text("현재 위치를 표시해야함", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(CurrentDistrictBoxBackgroundColor)
            ) {
                Text(
                    text = stringResource(R.string.district_current),
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

    HorizontalDivider(thickness = 1.dp, color = HorizontalDividerShadowColor)
    HorizontalDivider(thickness = 8.dp, color = HorizontalDividerColor)
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
            contentDescription = stringResource(R.string.district_home_icon_desc),
            modifier = Modifier
                .padding(end = 4.dp)
                .size(20.dp)
        )
        Text(text = stringResource(R.string.district_add_home), fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SearchDescription() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.district_search_description_title),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(text = stringResource(R.string.district_search_description_road_title))
        Text(
            text = stringResource(R.string.district_search_description_road_summary),
            fontSize = 14.sp,
            color = SearchDistrictDescriptionSummaryTextColor,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Text(text = stringResource(R.string.district_search_description_district_title))
        Text(
            text = stringResource(R.string.district_search_description_district_summary),
            fontSize = 14.sp,
            color = SearchDistrictDescriptionSummaryTextColor,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Text(text = stringResource(R.string.district_search_description_building_title))
        Text(
            text = stringResource(R.string.district_search_description_building_summary),
            fontSize = 14.sp,
            color = SearchDistrictDescriptionSummaryTextColor,
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}

@Composable
private fun SearchResultList(districtSearchList: List<District>) {
    if (districtSearchList.isEmpty()) {
        CircularProgressIndicator()
    } else {
        LazyColumn {
            items(districtSearchList.size) {
                val district = districtSearchList[it]
                Text(district.name)
            }
        }
    }
}
