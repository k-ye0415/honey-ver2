package com.jin.honey.feature.address.ui

import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.jin.honey.R
import com.jin.honey.feature.address.domain.model.SearchAddress
import com.jin.honey.feature.address.domain.model.AddressTag
import com.jin.honey.feature.address.domain.model.Address
import com.jin.honey.feature.ui.state.DbState
import com.jin.honey.ui.theme.DistrictSearchBoxBackgroundColor
import com.jin.honey.ui.theme.DistrictSearchHintTextColor
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.SearchDistrictDescriptionSummaryTextColor
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.overlay.Marker

@Composable
fun AddressDetailScreen(searchAddress: SearchAddress?, viewModel: AddressViewModel, onNavigateToMain: () -> Unit) {
    val context = LocalContext.current
    var keyword by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.insertState.collect {
            when (it) {
                is DbState.Success -> {
                    Toast.makeText(context, context.getString(R.string.district_toast_save_success), Toast.LENGTH_SHORT)
                        .show()
                    onNavigateToMain()
                }

                is DbState.Error -> {
                    if (it.message == context.getString(R.string.error_district_full)) {
                        showDialog = true
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.district_toast_save_fail),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
        Column(modifier = Modifier.padding(innerpadding)) {
            if (searchAddress == null) {
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
                                        searchAddress.coordinate.y,
                                        searchAddress.coordinate.x,
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
                    searchAddress.addressName.roadAddress,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(top = 16.dp)
                )
                Text(
                    "[지번] ${searchAddress.addressName.lotNumAddress}",
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
                        val address = Address(
                            id = null,
                            addressTag = AddressTag.CURRENT,
                            address = searchAddress,
                            addressDetail = keyword
                        )
                        viewModel.saveAddress(address, false)
                    }
                ) {
                    Text(text = "위치 지정", fontWeight = FontWeight.Bold)
                }
                if (showDialog) {
                    DialogNoti(onDismissDialog = { showDialog = false }, onDeleteAndSave = {
                        val address = Address(
                            id = null,
                            addressTag = AddressTag.CURRENT,
                            address = searchAddress,
                            addressDetail = keyword
                        )
                        viewModel.saveAddress(address, true)
                    })
                }
            }
        }
    }
}

@Composable
private fun DialogNoti(onDismissDialog: () -> Unit, onDeleteAndSave: () -> Unit) {
    //FIXME ui refactor
    AlertDialog(
        title = {
            Text(text = "꿀재료")
        },
        text = {
            Text(text = "주소는 최대 10개 저장할 수 있습니다. 가장 오래전 사용한 주소를 삭제하고 이 주소를 저장하시겠어요?")
        },
        onDismissRequest = {

        },
        confirmButton = {
            Button(onClick = onDismissDialog) {
                Text("취소")
            }
        },
        dismissButton = {
            Button(onClick = onDeleteAndSave) {
                Text("삭제 후 저장")
            }
        }
    )
}
