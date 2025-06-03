package com.jin.honey.feature.cart.domain

import com.jin.honey.feature.cart.domain.model.IngredientCart

interface CartRepository {
    suspend fun insertIngredientToCart(cart: IngredientCart)
}
