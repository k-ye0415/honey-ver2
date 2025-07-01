package com.jin.domain.usecase

import com.jin.domain.repositories.OrderRepository
import com.jin.domain.model.order.Order

class GetOrderHistoriesUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(): Result<List<Order>> {
        val orderList = repository.fetchOrderHistories()
        return if (orderList.isEmpty()) Result.failure(Exception("Order history is empty"))
        else Result.success(orderList)
    }
}
