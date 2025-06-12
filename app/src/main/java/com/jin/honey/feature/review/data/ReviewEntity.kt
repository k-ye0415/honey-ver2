package com.jin.honey.feature.review.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val writtenDateTime: Long,
    val menuName: String,
    val review: String,
    val totalScore: Double,
    val tasteScore: Double,
    val recipeScore: Double
)
