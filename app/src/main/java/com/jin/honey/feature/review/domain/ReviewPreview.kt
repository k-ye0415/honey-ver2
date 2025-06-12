package com.jin.honey.feature.review.domain

import com.jin.honey.feature.cart.domain.model.IngredientCart

data class ReviewPreview(
    val review: Review,
    val ingredients: List<IngredientCart>
)
