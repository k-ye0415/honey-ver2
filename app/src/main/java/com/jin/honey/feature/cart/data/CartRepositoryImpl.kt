package com.jin.honey.feature.cart.data

import com.jin.honey.feature.cart.data.model.CartEntity
import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.food.domain.model.Ingredient
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
                if (entity != null) {
                    val cartItems = entity.map { it.toDomainModel() }
                    Result.success(cartItems)
                } else {
                    Result.failure(Exception("Cart Items is empty"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeCartItem(cartItem: IngredientCart, ingredient: Ingredient) {
        try {
            withContext(Dispatchers.IO) {
                val newIngredients = cartItem.ingredients.filter { it.name != ingredient.name }
                val updateCartItem = cartItem.copy(ingredients = newIngredients)
                db.removeCartItem(updateCartItem.toEntityModel())
            }
        } catch (e: Exception) {
            //
        }
    }

    private fun IngredientCart.toEntityModel(): CartEntity {
        return CartEntity(
            id = id ?: 0,
            addedTime = addedCartInstant.toEpochMilli(),
            menuName = menuName,
            menuImageUrl = menuImageUrl,
            ingredients = ingredients,
            isOrdered = false
        )
    }

    private fun CartEntity.toDomainModel(): IngredientCart {
        return IngredientCart(
            id = id,
            addedCartInstant = Instant.ofEpochSecond(addedTime),
            menuName = menuName,
            menuImageUrl = menuImageUrl,
            ingredients = ingredients
        )
    }
}
