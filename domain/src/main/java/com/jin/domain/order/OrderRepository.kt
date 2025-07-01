package com.jin.domain.order

import com.jin.domain.cart.model.IngredientCart
import com.jin.domain.order.model.Order

interface OrderRepository {
    suspend fun savePayAndOrder(order: Order): Result<Unit>
    suspend fun fetchOrderHistories(): List<Order>
    suspend fun fetchOrderPayment(orderKey: String): Order?
    suspend fun fetchOrderIngredients(orderKey: String, menuName: String): List<IngredientCart>
}
