package com.jin.domain.usecase

import com.jin.domain.repositories.ReviewRepository
import com.jin.domain.model.review.Review

class WriteReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(reviews: List<Review>):Result<Unit> {
        return repository.writtenReviewSave(reviews)
    }
}
