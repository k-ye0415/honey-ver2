package com.jin.honey.feature.cart.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.model2.cart.CartKey

class ChangeQuantityOfCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(quantityMap: Map<CartKey, Int>): Result<Unit> {
        return repository.changeQuantityOfCartItems(quantityMap)
    }
}
