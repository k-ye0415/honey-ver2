package com.jin.honey.feature.ingredient.model

import com.jin.honey.feature.review.domain.Review
import com.jin.honey.feature.review.domain.ReviewRepository

class GetReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(menuName: String): Result<List<Review>> {
        return repository.fetchMenuReview(menuName)
    }
}
