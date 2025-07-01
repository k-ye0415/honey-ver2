package com.jin.domain.usecase

import com.jin.domain.cart.CartRepository
import com.jin.domain.cart.model.CartKey

class ChangeQuantityOfCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(quantityMap: Map<CartKey, Int>): Result<Unit> {
        return repository.changeQuantityOfCartItems(quantityMap)
    }
}
