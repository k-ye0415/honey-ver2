package com.jin.honey.feature.order.domain.usecase

import com.jin.domain.repositories.CartRepository
import com.jin.domain.model.order.Order
import com.jin.domain.repositories.OrderRepository

class PayAndOrderUseCase(private val orderRepository: OrderRepository, private val cartRepository: CartRepository) {
    suspend operator fun invoke(order: Order): Result<Unit> {
        val saveResult = orderRepository.savePayAndOrder(order)
        return if (saveResult.isSuccess) {
            cartRepository.updateOrderCartItem(order.cart)
            Result.success(Unit)
        } else {
            saveResult
        }
    }
}
