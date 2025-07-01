package com.jin.honey.feature.review.domain

import com.jin.domain.repositories.ReviewRepository

class SyncReviewsUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke() {
        repository.syncReviews()
    }
}
