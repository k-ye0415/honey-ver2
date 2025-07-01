package com.jin.domain.model.review

import com.jin.domain.food.model.CategoryType

data class ReviewRankPreview(
    val categoryType: CategoryType,
    val menuName: String,
    val menuImageUrl: String,
    val reviewScore: Double,
    val reviewCount: Int
)
