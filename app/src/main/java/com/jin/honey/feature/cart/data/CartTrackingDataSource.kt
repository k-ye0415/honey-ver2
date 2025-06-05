package com.jin.honey.feature.cart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jin.honey.feature.cart.data.model.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartTrackingDataSource {
    @Insert
    suspend fun insertIngredientToCart(cartEntity: CartEntity)

    @Query("SELECT * FROM cart WHERE isOrdered = 0 ORDER BY addedTime DESC")
    fun queryUnorderedCartItems(): Flow<List<CartEntity>>

    @Update
    suspend fun removeCartItem(cartEntity: CartEntity)

    @Update
    suspend fun changeQuantity(cartEntity: CartEntity)

    @Query("SELECT * FROM cart WHERE menuName = :menuName")
    suspend fun findIngredients(menuName: String): CartEntity
}
