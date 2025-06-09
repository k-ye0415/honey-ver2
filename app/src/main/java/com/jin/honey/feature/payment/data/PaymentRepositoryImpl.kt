package com.jin.honey.feature.payment.data

import com.jin.honey.feature.payment.data.model.PaymentEntity
import com.jin.honey.feature.payment.domain.Payment
import com.jin.honey.feature.payment.domain.PaymentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaymentRepositoryImpl(private val db: PayAndOrderTrackingDataSource) : PaymentRepository {
    override suspend fun savePayAndOrder(payment: Payment): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                db.savePayAndOrder(payment.toEntity())
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun Payment.toEntity(): PaymentEntity {
        return PaymentEntity(
            payDataTime = payInstant.toEpochMilli(),
            payState = payState.state,
            placeName = address.address.placeName,
            lotNumberAddress = address.address.addressName.lotNumAddress,
            roadAddress = address.address.addressName.roadAddress,
            detailAddress = address.addressDetail,
            cart = cart,
            requirement = requirement.requirement,
            riderRequirement = requirement.riderRequirement,
            productPrice = prices.productPrice,
            deliveryPrice = prices.deliveryPrice,
            totalPrice = prices.totalPrice
        )
    }
}
