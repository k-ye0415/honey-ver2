package com.jin.honey.feature.order.domain

import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.order.domain.model.Order

interface OrderRepository {
    suspend fun savePayAndOrder(order: Order): Result<Unit>
    suspend fun fetchOrderHistories(): Result<List<Order>>
    suspend fun fetchOrderPayment(orderKey: String): Result<Order>
    suspend fun fetchOrderIngredients(orderKey: String, menuName: String): Result<List<IngredientCart>>
}
