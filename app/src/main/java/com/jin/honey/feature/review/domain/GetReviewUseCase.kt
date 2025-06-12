package com.jin.honey.feature.review.domain

class GetReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(menuName: String): Result<List<Review>> {
        return repository.fetchMenuReview(menuName)
    }
}
