package com.jin.honey.feature.cart.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.IngredientCart

class GetCartItemsUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(): Result<List<IngredientCart>> = cartRepository.fetchUnorderedCartItems()
}
