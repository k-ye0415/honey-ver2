package com.jin.database.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jin.database.entities.AddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressTrackingDataSource {
    @Insert
    suspend fun saveAddress(entity: AddressEntity)

    @Update
    suspend fun updateAddress(entity: AddressEntity)

    @Query("UPDATE address SET isLatestAddress = :isLatestAddress")
    suspend fun clearSelectedAddress(isLatestAddress:Boolean)

    @Query("SELECT * FROM address ORDER BY id DESC")
    fun queryAllAddress(): Flow<List<AddressEntity>>

    @Query("SELECT * FROM address ORDER BY id ASC LIMIT 1")
    suspend fun oldestAddress(): AddressEntity

    @Delete
    suspend fun deleteLatestAddress(entity: AddressEntity): Int

    @Query("SELECT * FROM address ORDER BY id DESC LIMIT 1")
    suspend fun latestAddress(): AddressEntity
}
