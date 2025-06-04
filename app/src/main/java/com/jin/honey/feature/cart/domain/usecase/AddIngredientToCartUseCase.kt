package com.jin.honey.feature.cart.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.Cart

class AddIngredientToCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cart: Cart): Result<Unit> {
        return repository.saveIngredientToCart(cart)
    }
}
