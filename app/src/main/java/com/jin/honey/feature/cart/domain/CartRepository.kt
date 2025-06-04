package com.jin.honey.feature.cart.domain

import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.food.domain.model.Ingredient

interface CartRepository {
    suspend fun saveIngredientToCart(cart: IngredientCart): Result<Unit>
    suspend fun fetchUnorderedCartItems(): Result<List<IngredientCart>>
    suspend fun removeCartItem(cartItem: IngredientCart, ingredient: Ingredient)
}
