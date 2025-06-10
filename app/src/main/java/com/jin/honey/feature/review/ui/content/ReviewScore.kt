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
import com.jin.honey.feature.review.ui.fallbackData
import com.jin.honey.ui.theme.ReviewStarColor

@Composable
fun ReviewScore(){
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
