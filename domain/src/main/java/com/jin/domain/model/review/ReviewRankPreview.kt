package com.jin.domain.model.review

import com.jin.domain.model.food.CategoryType

data class ReviewRankPreview(
    val categoryType: CategoryType,
    val menuName: String,
    val menuImageUrl: String,
    val reviewScore: Double,
    val reviewCount: Int
)
