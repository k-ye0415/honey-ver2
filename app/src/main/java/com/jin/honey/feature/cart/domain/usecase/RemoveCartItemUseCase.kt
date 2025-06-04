package com.jin.honey.feature.cart.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.IngredientCart

class RemoveCartItemUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cartItem: Cart, ingredient: IngredientCart) {
        repository.removeCartItem(cartItem, ingredient)
    }
}
