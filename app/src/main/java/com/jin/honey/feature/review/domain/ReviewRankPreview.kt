package com.jin.honey.feature.review.domain

import com.jin.honey.feature.food.domain.model.CategoryType

data class ReviewRankPreview(
    val categoryType: CategoryType,
    val menuName: String,
    val menuImageUrl: String,
    val reviewScore: Double,
    val reviewCount: Int
)
