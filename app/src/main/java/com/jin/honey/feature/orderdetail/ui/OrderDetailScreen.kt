package com.jin.honey.feature.orderdetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.ui.theme.DistrictSearchHintTextColor
import com.jin.honey.ui.theme.FoodSearchBoxBorderColor
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PointColor

@Composable
fun OrderDetailScreen(cartItems: List<Cart>) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
        LazyColumn(modifier = Modifier.padding(innerpadding)) {
            item {
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
                        text = "주문하기",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            item {
                Column {
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.ic_current_location),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                        Text("자양로 117")
                        Text("(으)로 배달")
                        Icon(Icons.Default.ArrowForwardIos, contentDescription = "")
                    }
                    Text("서울특별시 자양로 117 광진구청 행정지원동 11")
                    Text("010-1234-1234")
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                ) {
                    // list
                    Column {
                        Column {
                            Row {
                                Text("메뉴 이름")
                                Text("전체 삭제")
                            }
                            HorizontalDivider()
                            Row {
                                AsyncImage(
                                    model = "",
                                    contentDescription = "",
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .size(50.dp)
                                        .background(Color.LightGray),
                                    contentScale = ContentScale.Crop
                                )
                                Column {
                                    Row {
                                        Text("재료 이름")
                                        Text("재료 용량")
                                        Text("재료 수량")
                                    }
                                }
                            }
                        }
                        val interactionSource = remember { MutableInteractionSource() }
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.LightGray)
                                    .indication(
                                        interactionSource = interactionSource,
                                        indication = rememberRipple(
                                            color = Color.Gray,
                                            bounded = true,
                                        )
                                    )
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null,
                                        onClick = {}
                                    )
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                    .padding(vertical = 4.dp, horizontal = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("옵션 변경", color = Color.Black)
                            }
                        }
                        HorizontalDivider()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "")
                            Text("메뉴 추가하기")
                        }
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                ) {
                    var keyword = ""
                    Column {
                        Text("요청사항")
                        Box(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .border(1.dp, FoodSearchBoxBorderColor, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                        ) {
                            BasicTextField(
                                value = keyword,
                                onValueChange = { keyword = it },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
//                            .focusRequester(focusRequester)
                                    .onFocusChanged { },
                                decorationBox = { innerTextField ->
                                    if (keyword.isEmpty()) {
                                        Text(
                                            text = stringResource(R.string.food_search_hint),
                                            color = DistrictSearchHintTextColor,
                                            fontSize = 16.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                        }
                        Row {
                            Checkbox(
                                checked = true,
                                onCheckedChange = { })
                            Text("다회용기에 담아주세요")
                            Icon(Icons.Default.Recycling, contentDescription = "")
                        }
                        HorizontalDivider()
                        Row {
                            Text("라이더 요청사항")
                            Text("요청사항 없음")
                            Icon(Icons.Default.ArrowForwardIos, contentDescription = "")
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                ) {
                    Column {
                        Row {
                            Text("결제수단")
                            Text("결제 방법 변경")
                            Icon(Icons.Default.ArrowForwardIos, contentDescription = "")
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.LightGray)
                        ) {
                            Text("무통장입금")
                        }
                    }
                }
            }
            item {
                Column {
                    Row {
                        Text("상품금액")
                        Text("22,000원")
                    }
                    Row {
                        Text("배달요금")
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)
                        ) {
                            Text("자세히")
                        }
                        Text("22,000원")
                    }
                    HorizontalDivider()
                    Row {
                        Text("총 결제 금액")
                        Text("24,000원")
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                ) {
                    Column {
                        Row {
                            Checkbox(false, {})
                            Text("약관 전체 동의")
                        }
                        HorizontalDivider()
                        Row {
                            Checkbox(false, {})
                            Text("이용약관 동의 (필수)")
                            Text("내용 보기")
                        }
                        Row {
                            Checkbox(false, {})
                            Text("개인정보 수집 및 이용 동의 (필수)")
                            Text("내용 보기")
                        }
                        Row {
                            Checkbox(false, {})
                            Text("전자 금융거래 이용약관 (필수)")
                            Text("내용 보기")
                        }
                        Row {
                            Checkbox(false, {})
                            Text("만 14세 이상 사용자 (필수)")
                            Text("내용 보기")
                        }
                        Row {
                            Checkbox(false, {})
                            Text("개인정보 제3자 제공 동의 (필수)")
                            Text("내용 보기")
                        }
                    }
                }
            }
            item {
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                    onClick = {}
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "24,000원 배달 결제하기", fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("1", color = PointColor, modifier = Modifier.scale(0.9f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OrderDetailScreenPreview() {
    HoneyTheme {
        OrderDetailScreen(emptyList())
    }
}
