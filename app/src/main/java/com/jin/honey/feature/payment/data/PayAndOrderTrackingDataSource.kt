package com.jin.honey.feature.payment.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.honey.feature.payment.data.model.PaymentEntity

@Dao
interface PayAndOrderTrackingDataSource {
    @Insert
    suspend fun insertPayment(paymentEntity: PaymentEntity)

    @Query("SELECT * FROM payAndOrder ORDER BY payDataTime DESC")
    suspend fun fetchAllOrdersByRecent(): List<PaymentEntity>

    @Query("SELECT * FROM payAndOrder WHERE orderKey = :orderKey")
    suspend fun queryOrderPayment(orderKey: String): PaymentEntity
}
