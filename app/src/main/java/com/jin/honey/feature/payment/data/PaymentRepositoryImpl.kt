package com.jin.honey.feature.payment.data

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

    override suspend fun fetchOrderHistory(): Result<List<Payment>> {
        return try {
            withContext(Dispatchers.IO) {
                val entities = db.fetchAllOrdersByRecent()
                val payments = entities.map { it.toDomainModel() }
                Result.success(payments)
            }
        } catch (e: Exception) {
            Result.failure(e)
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
