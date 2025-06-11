package com.jin.honey.feature.home.ui.content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.ReviewStarColor
import kotlinx.coroutines.delay
import java.text.DecimalFormat

@Composable
fun HomeReviewRanking() {
    var currentIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000L)
            val nextIndex = (currentIndex + 1) % dummyReviewRankings.size
            currentIndex = nextIndex
        }
    }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp)
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .background(
                        color = Color(0xFF007BFF),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 20.dp, vertical = 4.dp)
            ) {
                Text("리뷰랭킹", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            HorizontalDivider(modifier = Modifier.weight(1f))
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(10.dp)
        ) {
            AnimatedContent(targetState = dummyReviewRankings[currentIndex],
                transitionSpec = {
                    // 1. EnterTransition 정의
                    val enter = slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300))

                    // 2. ExitTransition 정의
                    val exit = slideOutVertically(
                        targetOffsetY = { fullHeight -> -fullHeight },
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300))

                    // 3. EnterTransition과 ExitTransition을 togetherWith로 결합하여 ContentTransform 반환
                    enter togetherWith exit using SizeTransform(clip = false)

                }
            ) { reviewItem ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    AsyncImage(
                        model = reviewItem.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .height(80.dp)
                            .width(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.White) // 랭크 박스 배경
                                .border(1.dp, PointColor, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_crown),
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = PointColor
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = reviewItem.rankText,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                            }
                        }
                        Text(
                            text = reviewItem.menuName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                tint = ReviewStarColor,
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            // ★★★ CountingFloatText 적용 ★★★
                            CountingFloatText(
                                targetValue = reviewItem.rating,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                ),
                                decimalPlaces = 1 // 소수점 한 자리
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            // ★★★ CountingText 적용 ★★★
                            CountingText(
                                targetValue = reviewItem.reviewCount,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                ),
                                suffix = "개" // "개" 접미사
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountingText(
    targetValue: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    durationMillis: Int = 500,
    prefix: String = "",
    suffix: String = ""
) {
    val animatedValue = remember { Animatable(0f) } // Float으로 애니메이션
    LaunchedEffect(targetValue) {
        // targetValue가 변경될 때마다 애니메이션 시작
        animatedValue.snapTo(animatedValue.value) // 현재 값에서 시작
        animatedValue.animateTo(
            targetValue.toFloat(),
            animationSpec = tween(durationMillis = durationMillis)
        )
    }

    Text(
        text = "$prefix${animatedValue.value.toInt()}$suffix",
        modifier = modifier,
        style = style
    )
}

@Composable
fun CountingFloatText(
    targetValue: Double,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    durationMillis: Int = 500,
    decimalPlaces: Int = 1, // 소수점 자릿수
    prefix: String = "",
    suffix: String = ""
) {
    val animatedValue = remember { Animatable(0f) }
    LaunchedEffect(targetValue) {
        animatedValue.snapTo(animatedValue.value)
        animatedValue.animateTo(
            targetValue.toFloat(),
            animationSpec = tween(durationMillis = durationMillis)
        )
    }

    val decimalFormat = remember { DecimalFormat("#." + "#".repeat(decimalPlaces)) } // 소수점 포맷
    Text(
        text = "$prefix${decimalFormat.format(animatedValue.value)}$suffix",
        modifier = modifier,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
fun HomeReviewRanking12() {
    HoneyTheme {
        HomeReviewRanking()
    }
}

// 더미 데이터 (실제 앱에서는 서버에서 가져오거나 다른 곳에서 정의)
data class ReviewRankingItem(
    val imageUrl: String,
    val rankText: String, // 예: "치킨 1위"
    val menuName: String,
    val rating: Double,
    val reviewCount: Int
)

val dummyReviewRankings = listOf(
    ReviewRankingItem(
        imageUrl = "https://picsum.photos/id/237/200/200",
        rankText = "치킨 1위",
        menuName = "황금올리브치킨",
        rating = 4.9,
        reviewCount = 2862
    ),
    ReviewRankingItem(
        imageUrl = "https://picsum.photos/id/238/200/200",
        rankText = "피자 1위",
        menuName = "페퍼로니 피자",
        rating = 4.8,
        reviewCount = 1500
    ),
    ReviewRankingItem(
        imageUrl = "https://picsum.photos/id/239/200/200",
        rankText = "중식 1위",
        menuName = "유니 짜장",
        rating = 4.7,
        reviewCount = 980
    ),
    ReviewRankingItem(
        imageUrl = "https://picsum.photos/id/240/200/200",
        rankText = "족발 1위",
        menuName = "보쌈 중",
        rating = 4.9,
        reviewCount = 3120
    )
)
