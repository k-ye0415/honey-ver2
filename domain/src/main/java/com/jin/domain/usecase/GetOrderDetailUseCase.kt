package com.jin.domain.usecase

import com.jin.domain.repositories.OrderRepository
import com.jin.domain.model.order.Order

class GetOrderDetailUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(orderKey: String): Result<Order> {
        val order = repository.fetchOrderPayment(orderKey)
        return if (order == null) Result.failure(Exception("order is null"))
        else Result.success(order)
    }
}
