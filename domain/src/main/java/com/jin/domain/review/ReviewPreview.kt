package com.jin.domain.review

import com.jin.domain.cart.model.IngredientCart

data class ReviewPreview(
    val review: Review,
    val ingredients: List<IngredientCart>
)
