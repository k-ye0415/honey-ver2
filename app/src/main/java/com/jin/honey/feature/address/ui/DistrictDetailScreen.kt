package com.jin.honey.feature.address.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.jin.honey.R
import com.jin.honey.feature.district.domain.model.District
import com.jin.honey.feature.district.domain.model.DistrictDetail
import com.jin.honey.feature.district.domain.model.DistrictType
import com.jin.honey.feature.district.domain.model.UserDistrict
import com.jin.honey.ui.theme.DistrictSearchBoxBackgroundColor
import com.jin.honey.ui.theme.DistrictSearchHintTextColor
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.SearchDistrictDescriptionSummaryTextColor
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.overlay.Marker

@Composable
fun DistrictDetailScreen(district: District?, viewModel: DistrictViewModel) {
    var keyword by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
        Column(modifier = Modifier.padding(innerpadding)) {
            if (district == null) {
                // FIXME : 처리 필요
            } else {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    IconButton({}) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = stringResource(R.string.ingredient_back_icon_desc),
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = "주소 상세 정보 입력",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                AndroidView(
                    factory = { ctx ->
                        MapView(ctx).apply {
                            // 지도를 초기화하고 설정
                            getMapAsync { naverMap ->
                                naverMap.uiSettings.isZoomControlEnabled = false

                                // 특정 좌표 설정
                                val targetLocation =
                                    LatLng(
                                        district?.coordinate?.y ?: 126.9780,
                                        district?.coordinate?.x ?: 37.5665,
                                    )

                                // 카메라 이동
                                val cameraUpdate = CameraUpdate.scrollTo(targetLocation)
                                naverMap.moveCamera(cameraUpdate)

                                // 마커 생성 및 지도에 추가
                                Marker().apply {
                                    position = targetLocation
                                    map = naverMap
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenWidthDp.dp)
                )

                Text(
                    district?.address?.roadAddress.orEmpty(),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(top = 16.dp)
                )
                Text(
                    "[지번] ${district?.address?.lotNumAddress.orEmpty()}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 16.dp),
                    color = SearchDistrictDescriptionSummaryTextColor
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(DistrictSearchBoxBackgroundColor, RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = keyword,
                        onValueChange = { keyword = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusChanged { },
                        decorationBox = { innerTextField ->
                            if (keyword.isEmpty()) {
                                Text(
                                    text = "상세주소를 입력하세요 (건물명, 동/호수 등)",
                                    color = DistrictSearchHintTextColor,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                    onClick = {
                        val userDistrict = UserDistrict(
                            id = null,
                            districtType = DistrictType.CURRENT,
                            district = district,
                            districtDetail = DistrictDetail(detailAddress = keyword)
                        )
                        viewModel.saveDistrict(userDistrict)
                    }
                ) {
                    Text(text = "위치 지정", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
