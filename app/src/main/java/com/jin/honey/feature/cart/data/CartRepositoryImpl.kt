package com.jin.honey.feature.cart.data

import com.jin.honey.feature.cart.data.model.CartEntity
import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepositoryImpl(private val db: CartTrackingDataSource) : CartRepository {
    override suspend fun saveIngredientToCart(cart: Cart): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                db.insertIngredientToCart(cart.toEntityModel())
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(Exception())
        }
    }

    private fun Cart.toEntityModel(): CartEntity {
        return CartEntity(
            addedTime = addedCartInstant.toEpochMilli(),
            menuName = menuName,
            ingredients = ingredients,
            isOrdered = false
        )
    }
}
