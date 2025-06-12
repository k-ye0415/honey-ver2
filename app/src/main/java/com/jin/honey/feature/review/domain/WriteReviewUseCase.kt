package com.jin.honey.feature.review.domain

class WriteReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(reviews: List<Review>):Result<Unit> {
        return repository.writtenReviewSave(reviews)
    }
}
