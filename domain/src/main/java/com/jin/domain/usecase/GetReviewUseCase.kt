package com.jin.domain.usecase

import com.jin.domain.review.ReviewRepository
import com.jin.domain.review.Review

class GetReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(menuName: String): Result<List<Review>> {
        val reviews = repository.fetchMenuReview(menuName)
        return if (reviews.isEmpty()) Result.failure(Exception("Review is empty")) else Result.success(reviews)
    }
}
