package com.jin.honey.feature.review.domain

interface ReviewRepository {
    suspend fun syncReviews()
    suspend fun writtenReviewSave(reviews: List<Review>): Result<Unit>
    suspend fun fetchMenuReview(menuName: String): Result<List<Review>>
    suspend fun fetchReviewByCategory(categoryName: String): Result<List<Review>>
}
