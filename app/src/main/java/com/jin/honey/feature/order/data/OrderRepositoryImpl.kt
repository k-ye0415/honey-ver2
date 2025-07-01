package com.jin.honey.feature.order.data

import com.jin.database.datasource.OrderTrackingDataSource
import com.jin.database.entities.OrderEntity
import com.jin.honey.feature.order.domain.OrderRepository
import com.jin.honey.feature.order.domain.model.Order
import com.jin.honey.feature.order.domain.model.PayPrice
import com.jin.honey.feature.order.domain.model.PaymentState
import com.jin.honey.feature.order.domain.model.Requirement
import com.jin.model.cart.IngredientCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class OrderRepositoryImpl(private val db: OrderTrackingDataSource) : OrderRepository {
    override suspend fun savePayAndOrder(order: Order): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                db.insertPayment(order.toEntity())
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchOrderHistories(): List<Order> = try {
        withContext(Dispatchers.IO) {
            val entities = db.fetchAllOrdersByRecent()
            entities.map { it.toDomainModel() }
        }
    } catch (e: Exception) {
        emptyList()
    }

    override suspend fun fetchOrderPayment(orderKey: String): Order? = try {
        withContext(Dispatchers.IO) {
            val entity = db.queryOrderPayment(orderKey)
            entity.toDomainModel()
        }
    } catch (e: Exception) {
        null
    }

    override suspend fun fetchOrderIngredients(orderKey: String, menuName: String): List<IngredientCart> {
        return try {
            withContext(Dispatchers.IO) {
                val entity = db.queryOrderPayment(orderKey)
                entity.cart.find { it.menuName == menuName }?.ingredients ?: emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun Order.toEntity(): OrderEntity {
        return OrderEntity(
            orderKey = orderKey,
            payDataTime = payInstant.toEpochMilli(),
            payState = payState.state,
            address = address,
            cart = cart,
            requirement = requirement.requirement,
            riderRequirement = requirement.riderRequirement,
            productPrice = prices.productPrice,
            deliveryPrice = prices.deliveryPrice,
            totalPrice = prices.totalPrice
        )
    }

    private fun OrderEntity.toDomainModel(): Order {
        return Order(
            id = id,
            orderKey = orderKey,
            payInstant = Instant.ofEpochMilli(payDataTime),
            payState = PaymentState.findByState(payState),
            address = address,
            cart = cart,
            requirement = Requirement(
                requirement = requirement,
                riderRequirement = riderRequirement
            ),
            prices = PayPrice(
                productPrice = productPrice,
                deliveryPrice = deliveryPrice,
                totalPrice = totalPrice
            )
        )
    }
}
