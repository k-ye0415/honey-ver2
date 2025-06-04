package com.jin.honey.feature.cart.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.food.domain.model.Ingredient

class RemoveCartItemUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cartItem: IngredientCart, ingredient: Ingredient) {
        repository.removeCartItem(cartItem, ingredient)
    }
}
