package com.jin.honey.feature.order.domain.usecase

import com.jin.domain.CartRepository
import com.jin.model.order.Order
import com.jin.domain.OrderRepository

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
