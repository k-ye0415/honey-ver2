package com.jin.honey.feature.review.domain

interface ReviewRepository {
    suspend fun writtenReviewSave(reviews: List<Review>): Result<Unit>
}
