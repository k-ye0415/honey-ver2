package com.jin.honey.feature.review.domain

import com.jin.model.review.Review

class WriteReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(reviews: List<Review>):Result<Unit> {
        return repository.writtenReviewSave(reviews)
    }
}
