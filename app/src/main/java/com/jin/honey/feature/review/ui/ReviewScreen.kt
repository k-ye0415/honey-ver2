package com.jin.honey.feature.review.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.review.ui.content.ReviewHeader
import com.jin.honey.feature.review.ui.content.ReviewItem
import com.jin.honey.feature.review.ui.content.ReviewScore
import com.jin.honey.feature.review.ui.content.ReviewShowOption
import com.jin.feature.ui.state.UiState

@Composable
fun ReviewScreen(viewModel: ReviewViewModel, menuName: String) {
    val reviewState by viewModel.reviewsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchReview(menuName)
    }

    val reviewContents = when (val state = reviewState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val reviews = reviewContents.map { it.review }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ReviewHeader(menuName)
            LazyColumn() {
                item {
                    ReviewScore(
                        totalScore = reviews.sumOf { it.reviewContent.totalScore } / reviewContents.size,
                        tasteScore = reviews.sumOf { it.reviewContent.tasteScore } / reviewContents.size,
                        recipeScore = reviews.sumOf { it.reviewContent.recipeScore } / reviewContents.size,
                        reviewCount = reviewContents.size
                    )
                }
                item {
                    HorizontalDivider(thickness = 4.dp)
                }
                item {
                    ReviewShowOption()
                }
                items(reviewContents.size) {
                    val item = reviewContents[it]
                    ReviewItem(item)
                }
            }
        }
    }
}

val ingredientFallback = listOf(
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
)
