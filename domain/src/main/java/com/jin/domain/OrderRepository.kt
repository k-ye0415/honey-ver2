package com.jin.domain

import com.jin.model.cart.IngredientCart
import com.jin.model.order.Order

interface OrderRepository {
    suspend fun savePayAndOrder(order: Order): Result<Unit>
    suspend fun fetchOrderHistories(): List<Order>
    suspend fun fetchOrderPayment(orderKey: String): Order?
    suspend fun fetchOrderIngredients(orderKey: String, menuName: String): List<IngredientCart>
}
