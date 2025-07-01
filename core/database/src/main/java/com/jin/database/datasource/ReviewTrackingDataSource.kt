package com.jin.database.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.database.entities.ReviewEntity

@Dao
interface ReviewTrackingDataSource {
    @Insert
    suspend fun insertWrittenReview(reviewEntity: ReviewEntity)

    @Query("SELECT * FROM review WHERE menuName = :menuName ORDER BY writtenDateTime DESC")
    suspend fun queryOnlyOneMenuReview(menuName: String): List<ReviewEntity>

    @Query("SELECT * FROM review WHERE categoryName = :categoryName")
    suspend fun queryReviewByCategory(categoryName: String): List<ReviewEntity>
}
