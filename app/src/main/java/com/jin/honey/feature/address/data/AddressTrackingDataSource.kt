package com.jin.honey.feature.address.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jin.honey.feature.address.data.model.AddressEntity

@Dao
interface AddressTrackingDataSource {
    @Insert
    suspend fun saveAddress(entity: AddressEntity)

    @Query("SELECT * FROM address")
    suspend fun queryAllAddress(): List<AddressEntity>?

    @Query("SELECT * FROM address ORDER BY id ASC LIMIT 1")
    suspend fun oldestAddress(): AddressEntity

    @Delete
    suspend fun deleteLatestAddress(entity: AddressEntity): Int

    @Query("SELECT * FROM address ORDER BY id DESC LIMIT 1")
    suspend fun latestAddress(): AddressEntity
}
