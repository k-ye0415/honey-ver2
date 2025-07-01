package com.jin.domain.usecase

import com.jin.domain.review.ReviewRepository
import com.jin.domain.review.Review

class WriteReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(reviews: List<Review>):Result<Unit> {
        return repository.writtenReviewSave(reviews)
    }
}
