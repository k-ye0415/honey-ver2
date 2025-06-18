package com.jin.honey.feature.payment.domain.usecase

import com.jin.honey.feature.payment.domain.PaymentRepository
import com.jin.honey.feature.payment.domain.model.Payment

class GetOrderDetailUseCase(private val repository: PaymentRepository) {
    suspend operator fun invoke(orderKey: String): Result<Payment> {
        val payment = repository.findOrderPaymentByOrderKey(orderKey)
        return if (payment == null) Result.failure(Exception("Payment is null"))
        else Result.success(payment)
    }
}
