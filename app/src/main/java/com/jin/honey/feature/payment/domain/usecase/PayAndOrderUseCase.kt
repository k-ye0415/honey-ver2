package com.jin.honey.feature.payment.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.payment.domain.model.Payment
import com.jin.honey.feature.payment.domain.PaymentRepository

class PayAndOrderUseCase(private val paymentRepository: PaymentRepository, private val cartRepository: CartRepository) {
    suspend operator fun invoke(payment: Payment): Result<Unit> {
        val saveResult = paymentRepository.savePayAndOrder(payment)
        return if (saveResult.isSuccess) {
            cartRepository.updateOrderCartItem(payment.cart)
            Result.success(Unit)
        } else {
            saveResult
        }
    }
}
