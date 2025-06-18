package com.jin.honey.feature.review.domain

class GetReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(menuName: String): Result<List<Review>> {
        val reviews = repository.fetchMenuReview(menuName)
        return if (reviews.isEmpty()) Result.failure(Exception("Review is empty")) else Result.success(reviews)
    }
}
