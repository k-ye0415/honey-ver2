package com.jin.honey.feature.review.domain

import com.jin.honey.feature.payment.domain.PaymentRepository

class GetReviewWithIngredientUseCase(
    private val repository: ReviewRepository,
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(menuName: String): Result<List<ReviewPreview>> {
        val reviewPreviews = mutableListOf<ReviewPreview>()
        val reviews = repository.fetchMenuReview(menuName)
        for (review in reviews) {
            val cart = paymentRepository.fetchOrderIngredients(review.orderKey, review.menuName)
            val reviewPreview = ReviewPreview(review = review, ingredients = cart)
            reviewPreviews.add(reviewPreview)
        }
        return if (reviewPreviews.isEmpty()) Result.failure(Exception("Review is empty"))
        else Result.success(reviewPreviews)
    }
}
