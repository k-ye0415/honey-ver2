package com.jin.domain.usecase

import com.jin.domain.review.ReviewRepository

class SyncReviewsUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke() {
        repository.syncReviews()
    }
}
