package com.jin.honey.feature.review.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReviewTrackingDataSource {
    @Insert
    suspend fun insertWrittenReview(reviewEntity: ReviewEntity)

    @Query("SELECT * FROM review WHERE menuName = :menuName ORDER BY writtenDateTime DESC")
    suspend fun queryOnlyOneMenuReview(menuName: String): List<ReviewEntity>
}
