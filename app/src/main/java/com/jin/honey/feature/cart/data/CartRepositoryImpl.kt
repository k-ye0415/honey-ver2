package com.jin.honey.feature.cart.data

import com.jin.honey.feature.cart.data.model.CartEntity
import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.IngredientCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepositoryImpl(private val db: CartTrackingDataSource) : CartRepository {
    override suspend fun insertIngredientToCart(cart: IngredientCart) {
        try {
            withContext(Dispatchers.IO) {
                db.insertIngredientToCart(cart.toEntityModel())
            }
        } catch (e: Exception) {
            // Silently ignore the error.
        }
    }

    private fun IngredientCart.toEntityModel(): CartEntity {
        return CartEntity(
            addedTime = addedCartInstant.toEpochMilli(),
            menuName = menuName,
            ingredients = ingredients,
            isOrdered = false
        )
    }
}
