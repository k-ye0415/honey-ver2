package com.jin.domain.usecase

import com.jin.domain.repositories.CartRepository
import com.jin.domain.model.cart.Cart

class RemoveIngredientInCartItemUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cartItem: Cart, ingredientName: String) {
        repository.removeIngredientInCartItem(cartItem, ingredientName)
    }
}
