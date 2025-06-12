package com.jin.honey.feature.review.domain

class SyncReviewsUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke() {
        repository.syncReviews()
    }
}
