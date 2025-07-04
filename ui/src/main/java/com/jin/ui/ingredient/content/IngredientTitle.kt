package com.jin.ui.ingredient.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.RoundedBoxButton
import com.jin.ui.R
import com.jin.domain.review.Review
import com.jin.ui.theme.AddRecipeBackgroundColor
import com.jin.ui.theme.AddRecipeBorderColor
import com.jin.ui.theme.AddRecipeRippleColor
import com.jin.ui.theme.PointColor
import com.jin.ui.theme.ReviewStarColor

@Composable
fun IngredientTitle(
    menuName: String,
    reviews: List<Review>,
    onClickShowReview: () -> Unit,
    onNavigateToRecipe: () -> Unit,
    onClickMyRecipe: () -> Unit
) {
    val totalScore = reviews.sumOf { it.reviewContent.totalScore } / reviews.size.toDouble()
    val formattedScore = String.format("%.1f", totalScore)
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        text = menuName,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        textAlign = TextAlign.Center
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        RoundedBoxButton(
            modifier = Modifier.wrapContentWidth(),
            shape = RoundedCornerShape(30.dp),
            backgroundColor = Color.White,
            borderColor = Color.LightGray,
            rippleColor = Color.Gray,
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
            onClick = onClickShowReview
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                tint = ReviewStarColor,
            )
            Text(
                text = stringResource(R.string.ingredient_review, formattedScore, reviews.size),
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = stringResource(R.string.ingredient_review_move_icon_desc)
            )
        }
        Spacer(Modifier.width(8.dp))
        RoundedBoxButton(
            modifier = Modifier.wrapContentWidth(),
            shape = RoundedCornerShape(30.dp),
            backgroundColor = Color.White,
            borderColor = PointColor,
            rippleColor = PointColor,
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
            onClick = onNavigateToRecipe
        ) {
            Text(
                text = stringResource(R.string.menu_recipe_button),
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
    Text(
        text = stringResource(R.string.ingredient_description),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        color = Color.Gray
    )
    RoundedBoxButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = AddRecipeBackgroundColor,
        borderColor = AddRecipeBorderColor,
        rippleColor = AddRecipeRippleColor,
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
        onClick = onClickMyRecipe
    ) {
        Text(
            text = stringResource(R.string.ingredient_add_my_recipe),
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}
