package com.jin.honey.feature.cart.domain

import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.food.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun saveIngredientToCart(cart: IngredientCart): Result<Unit>
    fun fetchUnorderedCartItems(): Flow<List<IngredientCart>>
    suspend fun removeCartItem(cartItem: IngredientCart, ingredient: Ingredient)
}
