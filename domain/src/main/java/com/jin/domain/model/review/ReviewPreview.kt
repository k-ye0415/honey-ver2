package com.jin.domain.model.review

import com.jin.domain.model.cart.IngredientCart

data class ReviewPreview(
    val review: Review,
    val ingredients: List<IngredientCart>
)
