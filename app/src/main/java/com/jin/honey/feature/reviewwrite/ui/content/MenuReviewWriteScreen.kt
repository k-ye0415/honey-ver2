package com.jin.honey.feature.reviewwrite.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.model.cart.Cart
import com.jin.model.review.ReviewContent
import com.jin.ui.theme.PointColor
import com.jin.ui.theme.ReviewStarColor
import com.jin.ui.theme.ReviewUnselectedStarColor

@Composable
fun MenuReviewWriteScreen(
    orderItems: Cart,
    btnText: String,
    onNextClick: (menuName: String, reviewContent: ReviewContent) -> Unit,
) {
    var reviewText by remember { mutableStateOf("") }
    val totalScore = remember { mutableDoubleStateOf(0.0) }
    val tasteScore = remember { mutableDoubleStateOf(0.0) }
    val recipeScore = remember { mutableDoubleStateOf(0.0) }
    val maxLength = 1000
    val minLines = 5
    val maxLines = 10

    val orderIngredientLabel = if (orderItems.ingredients.size > 1) {
        "${orderItems.ingredients.firstOrNull()?.name.orEmpty()} 외 ${orderItems.ingredients.size - 1}"
    } else {
        orderItems.ingredients.firstOrNull()?.name.orEmpty()
    }

    val isReviewValid = totalScore.value > 0.0
            && tasteScore.value > 0.0
            && recipeScore.value > 0.0
            && reviewText.length > 10

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 14.dp)
    ) {
        Text(orderItems.menuName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = orderIngredientLabel)
        SelectableReviewScoreBar(
            modifier = Modifier.padding(bottom = 8.dp),
            starSize = 48.dp,
            onRatingChanged = { score ->
                totalScore.value = score
            }
        )
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.review_score_taste_quantity))
            SelectableReviewScoreBar(
                modifier = Modifier,
                starSize = 32.dp,
                onRatingChanged = { score ->
                    tasteScore.value = score
                }
            )
        }
        Row(
            modifier = Modifier.padding(bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.review_score_recipe))
            SelectableReviewScoreBar(
                modifier = Modifier,
                starSize = 32.dp,
                onRatingChanged = { score ->
                    recipeScore.value = score
                }
            )
        }

        OutlinedTextField(
            value = reviewText,
            onValueChange = { newValue ->
                // 최대 글자 수를 넘지 않도록 제한
                if (newValue.length <= maxLength) {
                    reviewText = newValue
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = (20 * minLines).dp, max = (20 * maxLines).dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.review_write_hint),
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            textStyle = TextStyle(fontSize = 16.sp),
            singleLine = false,
            maxLines = maxLines,
        )
        Text(
            text = stringResource(R.string.review_write_current_max_length, reviewText.length),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.End
        )
        Button(
            enabled = isReviewValid,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
            onClick = {
                val reviewContent =  ReviewContent(
                    reviewContent = reviewText,
                    totalScore = totalScore.value,
                    tasteScore = tasteScore.value,
                    recipeScore = recipeScore.value,
                )
                onNextClick(orderItems.menuName, reviewContent)
            }
        ) {
            Text(text = btnText, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SelectableReviewScoreBar(
    modifier: Modifier,
    starSize: Dp,
    onRatingChanged: (Double) -> Unit
) {
    var currentRating by remember { mutableDoubleStateOf(0.0) }
    val maxStars = 5

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..maxStars) {
            val tintColor = if (currentRating >= i) ReviewStarColor else ReviewUnselectedStarColor

            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(R.string.review_write_score_icon_desc),
                tint = tintColor,
                modifier = Modifier
                    .size(starSize)
                    .clickable {
                        currentRating = i.toDouble()
                        onRatingChanged(currentRating)
                    }
            )
        }
    }
}
