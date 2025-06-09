package com.jin.honey.feature.payment.data

import androidx.room.Dao
import androidx.room.Insert
import com.jin.honey.feature.payment.data.model.PaymentEntity

@Dao
interface PayAndOrderTrackingDataSource {
    @Insert
    suspend fun insertPayment(paymentEntity: PaymentEntity)
}
