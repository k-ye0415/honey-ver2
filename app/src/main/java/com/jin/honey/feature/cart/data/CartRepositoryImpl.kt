package com.jin.honey.feature.cart.data

import com.jin.honey.feature.cart.data.model.CartEntity
import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.CartKey
import com.jin.honey.feature.food.domain.model.Ingredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant

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

    override fun fetchUnorderedCartItems(): Flow<List<Cart>> {
        return db.queryUnorderedCartItems()
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override suspend fun removeCartItem(cartItem: Cart, ingredient: Ingredient) {
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

    override suspend fun changeCartQuantity(quantityMap: Map<CartKey, Int>) {
        //
    }

    private suspend fun findIngredients(menuName: String): List<Ingredient>? {
        return try {
            withContext(Dispatchers.IO) {
                db.findIngredients(menuName)
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun Cart.toEntityModel(): CartEntity {
        return CartEntity(
            id = id ?: 0,
            addedTime = addedCartInstant.toEpochMilli(),
            menuName = menuName,
            menuImageUrl = menuImageUrl,
            ingredients = ingredients,
            isOrdered = false
        )
    }

    private fun CartEntity.toDomainModel(): Cart {
        return Cart(
            id = id,
            addedCartInstant = Instant.ofEpochMilli(addedTime),
            menuName = menuName,
            menuImageUrl = menuImageUrl,
            ingredients = ingredients
        )
    }
}
