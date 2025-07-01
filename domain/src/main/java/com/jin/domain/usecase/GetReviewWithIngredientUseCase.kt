package com.jin.domain.usecase

import com.jin.domain.repositories.OrderRepository
import com.jin.domain.repositories.ReviewRepository
import com.jin.domain.model.review.ReviewPreview

class GetReviewWithIngredientUseCase(
    private val repository: ReviewRepository,
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(menuName: String): Result<List<ReviewPreview>> {
        val reviewPreviews = mutableListOf< ReviewPreview>()
        val reviews = repository.fetchMenuReview(menuName)
        for (review in reviews) {
            val cart = orderRepository.fetchOrderIngredients(review.orderKey, review.menuName)
            val reviewPreview =  ReviewPreview(review = review, ingredients = cart)
            reviewPreviews.add(reviewPreview)
        }
        return if (reviewPreviews.isEmpty()) Result.failure(Exception("Review is empty"))
        else Result.success(reviewPreviews)
    }
}
