package com.jin.honey.feature.review.ui.content

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.model.review.ReviewPreview
import com.jin.ui.theme.ReviewBoxBorderColor
import com.jin.ui.theme.ReviewDateTextColor
import com.jin.ui.theme.ReviewDividerColor
import com.jin.ui.theme.ReviewScoreTitleTextColor
import com.jin.ui.theme.ReviewStarColor
import java.time.Duration
import java.time.Instant

@Composable
fun ReviewItem(review: ReviewPreview) {
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
            Text(text = reviewDataTimeLabel(review.review.reviewInstant), fontSize = 12.sp, color = ReviewDateTextColor)
            Spacer(Modifier.weight(1f))
            Text(text = stringResource(R.string.review_blocking), fontSize = 12.sp, color = ReviewDateTextColor)
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
            Text(
                "${review.review.reviewContent.totalScore}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 4.dp)
            )
            ReviewScoreTitleAndValue(
                stringResource(R.string.review_score_taste_quantity),
                review.review.reviewContent.tasteScore.toInt()
            )
            ReviewScoreTitleAndValue(
                stringResource(R.string.review_score_recipe),
                review.review.reviewContent.recipeScore.toInt()
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // 아이템 간 간격
        ) {
            items(review.ingredients.size) { index ->
                val ingredientItem = review.ingredients[index]
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

        Text(review.review.reviewContent.reviewContent, modifier = Modifier.fillMaxWidth())
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, ReviewBoxBorderColor, RoundedCornerShape(16.dp))
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_good),
                        contentDescription = stringResource(R.string.review_good_icon_desc),
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(12.dp),
                        tint = ReviewScoreTitleTextColor
                    )
                    Text(
                        "0",
                        fontSize = 12.sp,
                        modifier = Modifier.width(16.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = ReviewScoreTitleTextColor
                    )
                }
            }
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = ReviewDividerColor)
    }
}

@Composable
private fun ReviewScoreTitleAndValue(title: String, score: Int) {
    Text(text = title, fontSize = 12.sp, color = ReviewScoreTitleTextColor)
    Icon(
        modifier = Modifier.size(12.dp),
        imageVector = Icons.Default.Star,
        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
        tint = ReviewStarColor,
    )
    Text(
        "$score",
        fontSize = 12.sp,
        color = ReviewStarColor,
        modifier = Modifier.padding(end = 4.dp)
    )
}

private fun reviewDataTimeLabel(instant: Instant): String {
    val now = Instant.now()
    val duration = Duration.between(instant, now)

    val minutes = duration.toMinutes()
    val hours = duration.toHours()
    val days = duration.toDays()
    val weeks = days / 7
    val months = days / 30
    val years = days / 365

    return when {
        minutes < 1 -> "방금 전"
        minutes < 60 -> "${minutes}분 전"
        hours < 24 -> "${hours}시간 전"
        days < 7 -> "${days}일 전"
        weeks < 4 -> "${weeks}주 전"
        months < 12 -> "${months}달 전"
        else -> "${years}년 전"
    }
}
