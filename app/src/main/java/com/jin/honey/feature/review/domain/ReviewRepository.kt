package com.jin.honey.feature.review.domain

import com.jin.model.review.Review

interface ReviewRepository {
    suspend fun syncReviews()
    suspend fun writtenReviewSave(reviews: List<Review>): Result<Unit>
    suspend fun fetchMenuReview(menuName: String): List< Review>
    suspend fun fetchReviewByCategory(categoryName: String): List< Review>
}
