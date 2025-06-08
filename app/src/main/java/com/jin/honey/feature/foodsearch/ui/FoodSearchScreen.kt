package com.jin.honey.feature.foodsearch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBackIosNew
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.ui.theme.DistrictSearchHintTextColor
import com.jin.honey.ui.theme.FoodSearchBoxBorderColor
import com.jin.honey.ui.theme.FoodSearchReviewCountColor
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.ReviewStarColor

@Composable
fun FoodSearchScreen() {
    var keyword by remember { mutableStateOf("") }
    val fallbackData = listOf("이름1", "이름2", "이름3", "이름4", "이름1", "이름2")
    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
        Column(modifier = Modifier.padding(innerpadding)) {
            // toolbar
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton({}) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = stringResource(R.string.ingredient_back_icon_desc),
                        tint = Color.Black
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, FoodSearchBoxBorderColor, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.district_search_icon_desc),
                            modifier = Modifier.padding(end = 4.dp)
                        )
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
                }
            }
            // 추천 메뉴
            Text(
                text = stringResource(R.string.food_search_recommend_menu),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(fallbackData.size) { index ->
                    Column {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)
                        ) {
                            AsyncImage(
                                model = "",
                                contentDescription = stringResource(R.string.food_search_recommend_menu_img_desc),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .padding(start = 4.dp, top = 4.dp)
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(1.dp, PointColor, shape = CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_dessert),
                                    contentDescription = stringResource(R.string.food_search_recommend_menu_category_img_desc),
                                    modifier = Modifier.scale(0.7f)
                                )
                            }
                        }
                        Text(
                            "메뉴 이름",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                tint = ReviewStarColor,
                            )
                            Text("4.9", fontSize = 12.sp, lineHeight = 1.5.em, fontWeight = FontWeight.SemiBold)
                            Text("(2,862)", fontSize = 12.sp, lineHeight = 1.5.em, color = FoodSearchReviewCountColor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FoodSearchScreenPreview() {
    HoneyTheme {
        FoodSearchScreen()
    }
}
