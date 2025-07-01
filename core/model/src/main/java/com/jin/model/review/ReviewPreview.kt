package com.jin.model.review

import com.jin.model.cart.IngredientCart

data class ReviewPreview(
    val review: Review,
    val ingredients: List<IngredientCart>
)
