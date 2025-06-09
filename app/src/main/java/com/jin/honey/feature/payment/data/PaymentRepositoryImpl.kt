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

    override suspend fun findOrderHistory(): List<Payment> {
        return try {
            withContext(Dispatchers.IO) {
                val entities = db.selectAllOrderHistory()
                val payments = entities.map { it.toDomainModel() }
                payments
            }
        } catch (e: Exception) {
            println("YEJIN 여기서 에러야? ${e.printStackTrace()}")
            emptyList()
        }
    }

    private fun Payment.toEntity(): PaymentEntity {
        return PaymentEntity(
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
