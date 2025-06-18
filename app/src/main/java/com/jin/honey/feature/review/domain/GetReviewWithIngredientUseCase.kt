package com.jin.honey.feature.review.domain

import com.jin.honey.feature.order.domain.OrderRepository

class GetReviewWithIngredientUseCase(
    private val repository: ReviewRepository,
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(menuName: String): Result<List<ReviewPreview>> {
        val reviewPreviews = mutableListOf<ReviewPreview>()
        return repository.fetchMenuReview(menuName)
            .map { reviews ->
                for (review in reviews) {
                    orderRepository.fetchOrderIngredients(review.orderKey, review.menuName)
                        .onSuccess { ingredients ->
                            val reviewPreview = ReviewPreview(review = review, ingredients = ingredients)
                            reviewPreviews.add(reviewPreview)
                        }
                }
                reviewPreviews
            }
    }
}
