package com.jin.honey.feature.review.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.ReviewStarColor

@Composable
fun ReviewScreen(menuName: String) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // title
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton({}) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
                Text(
                    text = menuName,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            HorizontalDivider()
            LazyColumn() {
                // 별점
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(34.dp),
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                            tint = ReviewStarColor,
                        )
                        Text("4.7", fontWeight = FontWeight.Bold, fontSize = 34.sp)
                        Column(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .width(140.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("맛과 양", fontSize = 14.sp)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                        tint = ReviewStarColor,
                                    )
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                        tint = ReviewStarColor,
                                    )
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                        tint = ReviewStarColor,
                                    )
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                        tint = ReviewStarColor,
                                    )
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                        tint = ReviewStarColor,
                                    )
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("레시피", fontSize = 14.sp)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                        tint = ReviewStarColor,
                                    )
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                        tint = ReviewStarColor,
                                    )
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                        tint = ReviewStarColor,
                                    )
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                        tint = ReviewStarColor,
                                    )
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                        tint = ReviewStarColor,
                                    )
                                }
                            }
                            Text(
                                "리뷰 ${fallbackData.size}",
                                fontSize = 12.sp,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
                item {
                    HorizontalDivider(thickness = 4.dp)
                }
                item {
                    Row(modifier = Modifier.padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = false, onCheckedChange = {})
                        Text("사진리뷰 보기", fontSize = 14.sp)
                        Spacer(Modifier.weight(1f))
                        Row(modifier = Modifier.padding(end = 10.dp)) {
                            Text("최신순", fontSize = 14.sp)
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "")
                        }
                    }
                }
                items(fallbackData.size) {
                    val item = fallbackData[it]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        Row {
                            Text(
                                "**님",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(end = 2.dp)
                            )
                            Text("1주전", fontSize = 12.sp)
                            Spacer(Modifier.weight(1f))
                            Text("신고/차단", fontSize = 12.sp)
                        }
                        Row(
                            modifier = Modifier.padding(bottom = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                tint = ReviewStarColor,
                            )
                            Text("3.0", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 4.dp))
                            Text("맛과 양", fontSize = 12.sp)
                            Icon(
                                modifier = Modifier.size(12.dp),
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                tint = ReviewStarColor,
                            )
                            Text(
                                "3",
                                fontSize = 12.sp,
                                color = ReviewStarColor,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text("레시피", fontSize = 12.sp)
                            Icon(
                                modifier = Modifier.size(12.dp),
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                tint = ReviewStarColor,
                            )
                            Text(
                                "3",
                                fontSize = 12.sp,
                                color = ReviewStarColor,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                        AsyncImage(
                            model = "",
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(LocalConfiguration.current.screenWidthDp.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .padding(bottom = 14.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp) // 아이템 간 간격
                        ) {
                            items(ingredientFallback.size) { index ->
                                val ingredientItem = ingredientFallback[index]
                                Column(modifier = Modifier.padding(end = 10.dp)) {
                                    Text(
                                        "${ingredientItem.name} ${ingredientItem.quantity}",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = stringResource(
                                            R.string.order_detail_product_price_monetary,
                                            ingredientItem.unitPrice
                                        ),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        Text(item, modifier = Modifier.fillMaxWidth())
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 14.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                                    .padding(vertical = 4.dp, horizontal = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_good),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .size(12.dp)
                                    )
                                    Text(
                                        "1",
                                        fontSize = 12.sp,
                                        modifier = Modifier.width(16.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ReviewScreen12() {
    HoneyTheme {
        ReviewScreen("치즈버거")
    }
}

val fallbackData = listOf(
    "겉은 부드럽고 속은 촉촉한 치즈 오믈렛! 치즈가 쭉 늘어나면서 고소함이 가득해서 정말 맛있었어요.",
    "계란이 정말 부드러워서 입안에서 사르르 녹는 느낌이에요. 집에서 만든 것보다 훨씬 맛있습니다.",
    "치즈가 듬뿍 들어가서 고소함이 배가 됩니다. 아침 식사 대용으로 최고예요.",
    "간이 딱 맞아서 다른 소스 없이도 맛있게 즐길 수 있었습니다. 기본에 충실한 맛!",
    "따뜻할 때 먹으니 더욱 고소하고 부드러웠어요. 갓 만든 오믈렛이 최고죠!",
    "생각보다 양이 많아서 든든하게 먹었어요. 가성비도 훌륭합니다.",
    "치즈 오믈렛 맛집 찾고 있었는데, 드디어 인생 오믈렛을 찾았습니다! 재방문 의사 100%!",
    "아이들이 특히 좋아하는 메뉴예요. 부드러워서 안심하고 먹일 수 있습니다.",
    "깔끔하고 담백해서 질리지 않고 계속 먹을 수 있습니다. 속이 편안해요.",
    "케첩이나 핫소스 살짝 뿌려 먹으니 또 다른 매력이 있네요.",
    "빵이랑 같이 먹으니 더욱 든든하고 맛있었습니다. 브런치 메뉴로 최고!",
    "계란 비린 맛 전혀 없이 깔끔하고 고소합니다.",
    "부드러운 식감 덕분에 계속 손이 갑니다. 중독성 있어요.",
    "간단하지만 훌륭한 맛을 내는 오믈렛입니다. 추천해요!",
    "치즈가 아낌없이 들어가 있어서 정말 만족스러웠습니다.",
    "새우가 정말 통통하고 신선해서 만족스러웠어요. 버터 소스에 푹 담겨 더욱 맛있습니다.",
    "마늘이 듬뿍 들어가서 향긋하고 감칠맛이 폭발합니다. 느끼함 없이 깔끔해요.",
    "바게트 빵에 소스 듬뿍 찍어 먹으니 정말 꿀맛! 빵 추가는 필수입니다.",
    "술안주로 시켰는데, 술이 술술 넘어가는 맛이었어요. 와인과 찰떡궁합입니다.",
    "생각보다 양이 많아서 든든하게 먹었어요. 여럿이서 나눠 먹기 좋습니다.",
    "갈릭버터 쉬림프 맛집 찾고 있었는데, 드디어 인생 쉬림프를 찾았습니다! 재방문 의사 100%!",
    "따뜻하게 먹으니 더욱 맛있었어요. 버터의 풍미가 살아있습니다.",
    "깔끔하고 담백해서 질리지 않고 계속 먹을 수 있습니다. 속이 편안해요.",
    "다른 해산물 요리랑 같이 시켜도 잘 어울리는 메뉴예요. 밸런스가 좋습니다.",
    "새우가 정말 탱글탱글해서 식감이 아주 좋았습니다. 신선함이 느껴져요.",
    "이국적인 향신료 맛이 입맛을 돋우고, 먹을수록 매력에 빠집니다.",
    "집에서 해 먹고 싶을 정도로 맛있었습니다. 레시피가 궁금하네요.",
    "마늘 후레이크가 바삭해서 식감도 좋고 더욱 고소했습니다.",
    "올리브 오일과 버터의 조합이 완벽해서 소스까지 다 먹었어요."
)

val ingredientFallback = listOf(
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
)
