package com.jin.honey.feature.review.data

import com.jin.honey.feature.review.domain.Review
import com.jin.honey.feature.review.domain.ReviewContent
import com.jin.honey.feature.review.domain.ReviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

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

    override suspend fun fetchMenuReview(menuName: String): Result<List<Review>> {
        return try {
            withContext(Dispatchers.IO) {
                val entities = db.queryOnlyOneMenuReview(menuName)
                val reviews = mutableListOf<Review>()
                for (entity in entities) {
                    reviews.add(entity.toDomain())
                }
                Result.success(reviews)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun Review.toEntity(): ReviewEntity {
        return ReviewEntity(
            id = id ?: 0,
            writtenDateTime = reviewInstant.toEpochMilli(),
            menuName = menuName,
            review = reviewContent.reviewContent,
            totalScore = reviewContent.totalScore,
            tasteScore = reviewContent.tasteScore,
            recipeScore = reviewContent.recipeScore
        )
    }

    private fun ReviewEntity.toDomain(): Review {
        return Review(
            id = id,
            reviewInstant = Instant.ofEpochMilli(writtenDateTime),
            menuName = menuName,
            reviewContent = ReviewContent(
                reviewContent = review,
                totalScore = totalScore,
                tasteScore = tasteScore,
                recipeScore = recipeScore
            )
        )
    }
}
