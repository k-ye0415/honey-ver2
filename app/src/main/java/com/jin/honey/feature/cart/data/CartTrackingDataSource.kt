package com.jin.honey.feature.cart.data

import androidx.room.Dao
import androidx.room.Insert
import com.jin.honey.feature.cart.data.model.CartEntity

@Dao
interface CartTrackingDataSource {
    @Insert
    suspend fun insertIngredientToCart(cartEntity: CartEntity)
}
