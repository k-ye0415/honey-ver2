package com.jin.honey.feature.review.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ReviewTrackingDataSource {
    @Insert
    suspend fun insertWrittenReview(reviewEntity: ReviewEntity)
}
