package com.jin.honey.feature.review.data

import com.jin.honey.feature.review.domain.Review
import com.jin.honey.feature.review.domain.ReviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewRepositoryImpl(private val db: ReviewTrackingDataSource) : ReviewRepository {
    override suspend fun writtenReviewSave(reviews: List<Review>): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                for (review in reviews) {
                    db.insertWrittenReview(review.toEntity())
                }
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun Review.toEntity(): ReviewEntity {
        return ReviewEntity(
            writtenDateTime = reviewInstant.toEpochMilli(),
            menuName = menuName,
            review = reviewContent.reviewContent,
            totalScore = reviewContent.totalScore,
            tasteScore = reviewContent.tasteScore,
            recipeScore = reviewContent.recipeScore
        )
    }
}
