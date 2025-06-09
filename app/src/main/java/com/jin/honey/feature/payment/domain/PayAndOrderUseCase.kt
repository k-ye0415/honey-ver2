package com.jin.honey.feature.payment.domain

import com.jin.honey.feature.cart.domain.CartRepository

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
