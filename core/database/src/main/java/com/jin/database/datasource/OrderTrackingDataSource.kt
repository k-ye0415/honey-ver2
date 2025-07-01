package com.jin.database.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.database.entities.OrderEntity

@Dao
interface OrderTrackingDataSource {
    @Insert
    suspend fun insertPayment(orderEntity: OrderEntity)

    @Query("SELECT * FROM payAndOrder ORDER BY payDataTime DESC")
    suspend fun fetchAllOrdersByRecent(): List<OrderEntity>

    @Query("SELECT * FROM payAndOrder WHERE orderKey = :orderKey")
    suspend fun queryOrderPayment(orderKey: String): OrderEntity
}
