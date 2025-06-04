package com.jin.honey.feature.cart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jin.honey.feature.cart.data.model.CartEntity
import com.jin.honey.feature.food.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface CartTrackingDataSource {
    @Insert
    suspend fun insertIngredientToCart(cartEntity: CartEntity)

    @Query("SELECT * FROM cart WHERE isOrdered = 0 ORDER BY addedTime DESC")
    fun queryUnorderedCartItems(): Flow<List<CartEntity>>

    @Update
    suspend fun removeCartItem(cartEntity: CartEntity)

    @Query("UPDATE cart SET quantity = :quantity WHERE menuName = :menuName")
    suspend fun changeQuantity(quantity: Int, menuName: String)

    @Query("SELECT ingredients FROM cart WHERE menuName = :menuName")
    suspend fun findIngredients(menuName: String): List<Ingredient>?
}
