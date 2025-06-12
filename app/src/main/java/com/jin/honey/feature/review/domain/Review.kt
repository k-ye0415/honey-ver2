package com.jin.honey.feature.review.domain

import java.time.Instant

data class Review(
    val id: Int?,
    val reviewInstant: Instant,
    val menuName: String,
    val reviewContent: ReviewContent,
)

data class ReviewContent(
    val reviewContent: String,
    val totalScore: Double,
    val tasteScore: Double,
    val recipeScore: Double,
)
