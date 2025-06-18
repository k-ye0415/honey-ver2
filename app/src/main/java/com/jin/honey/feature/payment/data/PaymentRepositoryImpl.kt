package com.jin.honey.feature.payment.data

import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.payment.data.model.PaymentEntity
import com.jin.honey.feature.payment.domain.PaymentRepository
import com.jin.honey.feature.payment.domain.model.PayPrice
import com.jin.honey.feature.payment.domain.model.Payment
import com.jin.honey.feature.payment.domain.model.PaymentState
import com.jin.honey.feature.payment.domain.model.Requirement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class PaymentRepositoryImpl(private val db: PayAndOrderTrackingDataSource) : PaymentRepository {
    override suspend fun savePayAndOrder(payment: Payment): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                db.insertPayment(payment.toEntity())
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchOrderHistories(): List<Payment> = try {
        withContext(Dispatchers.IO) {
            val entities = db.fetchAllOrdersByRecent()
            entities.map { it.toDomainModel() }
        }
    } catch (e: Exception) {
        emptyList()
    }

    override suspend fun findOrderPaymentByOrderKey(orderKey: String): Payment? = try {
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

    private fun Payment.toEntity(): PaymentEntity {
        return PaymentEntity(
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

    private fun PaymentEntity.toDomainModel(): Payment {
        return Payment(
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
