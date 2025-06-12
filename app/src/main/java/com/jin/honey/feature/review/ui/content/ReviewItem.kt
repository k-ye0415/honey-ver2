package com.jin.honey.feature.review.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.review.ui.ingredientFallback
import com.jin.honey.ui.theme.ReviewBoxBorderColor
import com.jin.honey.ui.theme.ReviewDateTextColor
import com.jin.honey.ui.theme.ReviewDividerColor
import com.jin.honey.ui.theme.ReviewScoreTitleTextColor
import com.jin.honey.ui.theme.ReviewStarColor

@Composable
fun ReviewItem(item: String) {
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
            Text("1주전", fontSize = 12.sp, color = ReviewDateTextColor)
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
            Text("3.0", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 4.dp))
            ReviewScoreTitleAndValue(stringResource(R.string.review_score_taste_quantity), 3)
            ReviewScoreTitleAndValue(stringResource(R.string.review_score_recipe), 4)
        }
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
                        "1",
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
