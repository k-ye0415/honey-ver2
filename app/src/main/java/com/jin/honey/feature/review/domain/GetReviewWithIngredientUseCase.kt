package com.jin.honey.feature.review.domain

import com.jin.honey.feature.payment.domain.PaymentRepository

class GetReviewWithIngredientUseCase(
    private val repository: ReviewRepository,
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(menuName: String): Result<List<ReviewPreview>> {
        val reviewPreviews = mutableListOf<ReviewPreview>()
        return repository.fetchMenuReview(menuName)
            .map { reviews ->
                for (review in reviews) {
                    paymentRepository.fetchOrderIngredients(review.orderKey, review.menuName)
                        .onSuccess { ingredients ->
                            val reviewPreview = ReviewPreview(review = review, ingredients = ingredients)
                            reviewPreviews.add(reviewPreview)
                        }
                }
                reviewPreviews
            }
    }
}
