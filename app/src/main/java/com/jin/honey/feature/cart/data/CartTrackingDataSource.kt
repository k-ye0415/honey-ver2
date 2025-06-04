package com.jin.honey.feature.cart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.honey.feature.cart.data.model.CartEntity

@Dao
interface CartTrackingDataSource {
    @Insert
    suspend fun insertIngredientToCart(cartEntity: CartEntity)

    @Query("SELECT * FROM cart WHERE isOrdered = 0 ORDER BY addedTime DESC")
    suspend fun queryUnorderedCartItems(): List<CartEntity>?
}
