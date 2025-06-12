package com.jin.honey.feature.review.data

import android.util.Log
import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.review.domain.Review
import com.jin.honey.feature.review.domain.ReviewContent
import com.jin.honey.feature.review.domain.ReviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class ReviewRepositoryImpl(
    private val db: ReviewTrackingDataSource,
    private val fireStoreDataSource: FireStoreDataSource
) : ReviewRepository {

    override suspend fun syncReviews() {
        for (docName in DOCUMENT_NAME_LIST) {
            fireStoreDataSource.fetchAllReviewWithMenus(docName)
                .onSuccess { writtenReviewSave(it) }
                .onFailure { Log.e(TAG, "sync review is fail\n${it.printStackTrace()}") }
        }
    }

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
            orderKey = orderKey,
            reviewKey = reviewKey,
            writtenDateTime = reviewInstant.toEpochMilli(),
            categoryName = categoryType.categoryName,
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
            orderKey = orderKey,
            reviewKey = reviewKey,
            reviewInstant = Instant.ofEpochMilli(writtenDateTime),
            categoryType = CategoryType.findByFirebaseDoc(categoryName),
            menuName = menuName,
            reviewContent = ReviewContent(
                reviewContent = review,
                totalScore = totalScore,
                tasteScore = tasteScore,
                recipeScore = recipeScore
            )
        )
    }

    private companion object {
        val TAG = "ReviewRepository"
        val DOCUMENT_NAME_LIST = listOf(
            "burger", "korean", "western", "chinese", "japanese", "snack", "vegan", "dessert",
        )
    }
}
