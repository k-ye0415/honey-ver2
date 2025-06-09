package com.jin.honey.feature.cart.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.Cart

class RemoveIngredientInCartItemUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cartItem: Cart, ingredientName: String) {
        repository.removeIngredientInCartItem(cartItem, ingredientName)
    }
}
