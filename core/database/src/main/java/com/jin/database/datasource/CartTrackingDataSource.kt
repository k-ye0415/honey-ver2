package com.jin.database.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jin.database.entities.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartTrackingDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartEntity: CartEntity)

    @Query("SELECT * FROM cart WHERE isOrdered = 0 ORDER BY addedTime DESC")
    fun queryUnorderedCartItems(): Flow<List<CartEntity>>

    @Delete
    suspend fun removeCartItem(cartEntity: CartEntity)

    @Update
    suspend fun changeCartItem(cartEntity: CartEntity)

    @Query("SELECT * FROM cart WHERE menuName = :menuName")
    suspend fun queryCartItem(menuName: String): CartEntity
}
