package com.jin.data.review

import android.util.Log
import com.jin.database.datasource.ReviewTrackingDataSource
import com.jin.database.entities.ReviewEntity
import com.jin.data.firestore.FireStoreDataSource
import com.jin.domain.review.ReviewRepository
import com.jin.domain.food.model.CategoryType
import com.jin.domain.review.Review
import com.jin.domain.review.ReviewContent
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

    override suspend fun fetchMenuReview(menuName: String): List<Review> {
        return try {
            withContext(Dispatchers.IO) {
                val entities = db.queryOnlyOneMenuReview(menuName)
                val reviews = mutableListOf<Review>()
                for (entity in entities) {
                    reviews.add(entity.toDomain())
                }
                return@withContext reviews
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun fetchReviewByCategory(categoryName: String): List<Review> {
        return try {
            withContext(Dispatchers.IO) {
                val entities = db.queryReviewByCategory(categoryName)
                val reviews = mutableListOf<Review>()
                for (entity in entities) {
                    reviews.add(entity.toDomain())
                }
                return@withContext reviews
            }
        } catch (e: Exception) {
            emptyList()
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
        return  Review(
            id = id,
            orderKey = orderKey,
            reviewKey = reviewKey,
            reviewInstant = Instant.ofEpochMilli(writtenDateTime),
            categoryType =  CategoryType.findByFirebaseDoc(categoryName),
            menuName = menuName,
            reviewContent =  ReviewContent(
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
