package com.jin.domain.usecase

import com.jin.domain.cart.CartRepository
import com.jin.domain.cart.model.Cart
import kotlinx.coroutines.flow.Flow

class GetCartItemsUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(): Flow<List<Cart>> = cartRepository.fetchUnorderedCartItems()
}
