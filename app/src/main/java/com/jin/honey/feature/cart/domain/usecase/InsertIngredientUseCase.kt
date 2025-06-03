package com.jin.honey.feature.cart.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.IngredientCart

class InsertIngredientUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cart: IngredientCart) {
        repository.insertIngredientToCart(cart)
    }
}
