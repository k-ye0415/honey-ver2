package com.jin.honey.feature.review.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.ui.theme.ReviewStarColor

@Composable
fun ReviewScore(totalScore: Double, tasteScore: Double, recipeScore: Double, reviewCount: Int) {
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
        Text("$totalScore", fontWeight = FontWeight.Bold, fontSize = 34.sp)
        Column(
            modifier = Modifier
                .padding(start = 20.dp)
                .width(140.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.review_score_taste_quantity), fontSize = 14.sp)
                RatingBar(tasteScore)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.review_score_recipe), fontSize = 14.sp)
                RatingBar(recipeScore)
            }
            Text(
                text = stringResource(R.string.review_count, reviewCount),
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun RatingBar(rating: Double) {
    val maxStars = 5
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        for (i in 1..maxStars) {
            val starIcon = when {
                rating >= i -> Icons.Filled.Star // 현재 점수가 별의 인덱스보다 크거나 같으면 꽉 찬 별
                rating > i - 1 -> Icons.Filled.StarHalf // 현재 점수가 별의 인덱스보다 크고 바로 이전 별보다는 크면 반 별
                else -> Icons.Outlined.StarOutline // 그 외는 빈 별
            }
            Icon(
                imageVector = starIcon,
                contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                tint = ReviewStarColor,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
