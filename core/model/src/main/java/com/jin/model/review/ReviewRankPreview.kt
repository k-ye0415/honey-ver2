package com.jin.model.review

import com.jin.model.food.CategoryType

data class ReviewRankPreview(
    val categoryType: CategoryType,
    val menuName: String,
    val menuImageUrl: String,
    val reviewScore: Double,
    val reviewCount: Int
)
