package com.jin.honey.feature.review.domain

import com.jin.domain.ReviewRepository
import com.jin.model.review.Review

class GetReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(menuName: String): Result<List<Review>> {
        val reviews = repository.fetchMenuReview(menuName)
        return if (reviews.isEmpty()) Result.failure(Exception("Review is empty")) else Result.success(reviews)
    }
}
