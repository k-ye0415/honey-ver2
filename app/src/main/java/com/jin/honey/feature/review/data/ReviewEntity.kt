package com.jin.honey.feature.review.data

import androidx.room.Entity

@Entity(tableName = "review", primaryKeys = ["reviewKey"])
data class ReviewEntity(
    val id: Int = 0,
    val orderKey: String,
    val reviewKey: String,
    val writtenDateTime: Long,
    val categoryName: String,
    val menuName: String,
    val review: String,
    val totalScore: Double,
    val tasteScore: Double,
    val recipeScore: Double
)
