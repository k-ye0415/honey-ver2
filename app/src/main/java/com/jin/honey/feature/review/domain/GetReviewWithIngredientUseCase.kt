package com.jin.honey.feature.review.domain

import com.jin.honey.feature.payment.domain.PaymentRepository

class GetReviewWithIngredientUseCase(
    private val repository: ReviewRepository,
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(menuName: String): Result<List<ReviewPreview>> {
        val reviewPreviews = mutableListOf<ReviewPreview>()
        val reviews = repository.fetchMenuReview(menuName)
            .map { reviews ->
//                for (review in reviews) {
                    paymentRepository.fetchOrderIngredients(reviews.orderKey, reviews.menuName)
                        .onSuccess { ingredients ->
                            val reviewPreview = ReviewPreview(review = reviews, ingredients = ingredients)
                            reviewPreviews.add(reviewPreview)
                        }
//                }
//                reviewPreviews
            }
        return if (reviewPreviews.isEmpty()) Result.failure(Exception("Review is empty")) else Result.success(reviewPreviews)
    }
}
