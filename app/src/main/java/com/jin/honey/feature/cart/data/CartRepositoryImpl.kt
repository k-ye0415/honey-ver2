package com.jin.honey.feature.cart.data

import com.jin.honey.feature.cart.data.model.CartEntity
import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.IngredientCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class CartRepositoryImpl(private val db: CartTrackingDataSource) : CartRepository {
    override suspend fun saveIngredientToCart(cart: IngredientCart): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                db.insertIngredientToCart(cart.toEntityModel())
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(Exception())
        }
    }

    override suspend fun fetchUnorderedCartItems(): Result<List<IngredientCart>> {
        return try {
            withContext(Dispatchers.IO) {
                val entity = db.queryUnorderedCartItems()
                val cartItems = entity.map { it.toDomainModel() }
                Result.success(cartItems)
            }
        } catch (e: Exception) {
            Result.failure(e)
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

    private fun CartEntity.toDomainModel(): IngredientCart {
        return IngredientCart(
            addedCartInstant = Instant.ofEpochSecond(addedTime),
            menuName = menuName,
            ingredients = ingredients
        )
    }
}
