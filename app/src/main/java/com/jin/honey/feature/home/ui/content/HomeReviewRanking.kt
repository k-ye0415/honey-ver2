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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.review.domain.ReviewRankPreview
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.ReviewRankingBoxBackgroundColor
import com.jin.honey.ui.theme.ReviewRankingBoxBorderColor
import com.jin.honey.ui.theme.ReviewRankingColor
import com.jin.honey.ui.theme.ReviewRankingContentColor
import com.jin.honey.ui.theme.ReviewRankingRemoteBoxBackgroundColor
import com.jin.honey.ui.theme.ReviewStarColor
import kotlinx.coroutines.delay
import java.text.DecimalFormat

@Composable
fun HomeReviewRanking(
    reviewRankList: List<ReviewRankPreview>,
    onNavigateToIngredient: (menuName: String) -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000L)
            val nextIndex = (currentIndex + 1) % reviewRankList.size
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
                        color = ReviewRankingColor,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 20.dp, vertical = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_review_ranking),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            HorizontalDivider(modifier = Modifier.weight(1f))
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(ReviewRankingRemoteBoxBackgroundColor)
                .padding(10.dp)
                .clickable { onNavigateToIngredient(reviewRankList[currentIndex].menuName) }
        ) {
            AnimatedContent(
                targetState = reviewRankList[currentIndex],
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
                        model = reviewItem.menuImageUrl,
                        contentDescription = stringResource(R.string.home_review_ranking_img_desc),
                        modifier = Modifier
                            .height(80.dp)
                            .width(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .height(80.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(ReviewRankingBoxBackgroundColor)
                                .border(1.dp, ReviewRankingBoxBorderColor, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_crown),
                                    contentDescription = stringResource(R.string.home_review_ranking_icon_desc),
                                    modifier = Modifier.size(14.dp),
                                    tint = ReviewRankingContentColor
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${reviewItem.categoryType.categoryName} 1위",
                                    fontSize = 12.sp,
                                    color = ReviewRankingContentColor,
                                    fontWeight = FontWeight.Bold
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
                                modifier = Modifier
                                    .padding(end = 2.dp)
                                    .size(16.dp),
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                tint = ReviewStarColor,
                            )
                            ReviewScoreCountingFloatText(
                                targetValue = reviewItem.reviewScore,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            ReviewCountingText(targetValue = reviewItem.reviewCount)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewCountingText(targetValue: Int) {
    val animatedValue = remember { Animatable(0f) } // Float으로 애니메이션
    LaunchedEffect(targetValue) {
        // targetValue가 변경될 때마다 애니메이션 시작
        animatedValue.snapTo(animatedValue.value) // 현재 값에서 시작
        animatedValue.animateTo(
            targetValue.toFloat(),
            animationSpec = tween(durationMillis = 500)
        )
    }

    Text(
        text = stringResource(R.string.home_review_ranking_count, animatedValue.value.toInt()),
        style = TextStyle(
            fontSize = 14.sp,
            color = PointColor
        ),
    )
}

@Composable
private fun ReviewScoreCountingFloatText(
    targetValue: Double,
    modifier: Modifier,
) {
    val animatedValue = remember { Animatable(0f) }
    val decimalFormat = remember { DecimalFormat("#." + "#".repeat(1)) } // 소수점 포맷
    LaunchedEffect(targetValue) {
        animatedValue.snapTo(animatedValue.value)
        animatedValue.animateTo(
            targetValue.toFloat(),
            animationSpec = tween(durationMillis = 500)
        )
    }

    Text(
        text = decimalFormat.format(animatedValue.value),
        modifier = modifier,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        ),
    )
}
