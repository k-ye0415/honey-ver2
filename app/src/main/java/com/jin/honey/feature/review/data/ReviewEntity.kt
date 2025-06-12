package com.jin.honey.feature.review.data

import androidx.room.Entity

@Entity(tableName = "review", primaryKeys = ["reviewKey"])
data class ReviewEntity(
    val id: Int = 0,
    val reviewKey: String,
    val writtenDateTime: Long,
    val menuName: String,
    val review: String,
    val totalScore: Double,
    val tasteScore: Double,
    val recipeScore: Double
)
