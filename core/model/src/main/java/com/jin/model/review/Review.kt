package com.jin.model.review

import com.jin.model.food.CategoryType
import java.time.Instant

data class Review(
    val id: Int?,
    val orderKey: String,
    val reviewKey: String,
    val reviewInstant: Instant,
    val categoryType: CategoryType,
    val menuName: String,
    val reviewContent: ReviewContent,
)

data class ReviewContent(
    val reviewContent: String,
    val totalScore: Double,
    val tasteScore: Double,
    val recipeScore: Double,
)
