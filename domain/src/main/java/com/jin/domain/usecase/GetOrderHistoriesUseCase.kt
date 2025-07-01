package com.jin.domain.usecase

import com.jin.domain.order.OrderRepository
import com.jin.domain.order.model.Order

class GetOrderHistoriesUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(): Result<List<Order>> {
        val orderList = repository.fetchOrderHistories()
        return if (orderList.isEmpty()) Result.failure(Exception("Order history is empty"))
        else Result.success(orderList)
    }
}
