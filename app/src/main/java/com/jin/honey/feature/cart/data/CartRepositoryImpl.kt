package com.jin.honey.feature.cart.data

import com.jin.honey.feature.cart.data.model.CartEntity
import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.CartKey
import com.jin.honey.feature.food.domain.model.CategoryType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant

class CartRepositoryImpl(private val db: CartTrackingDataSource) : CartRepository {
    override suspend fun saveCartItem(cart: Cart): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                val cartEntity = findCartItem(cart.menuName)
                if (cartEntity != null) {
                    val updateCart = cartEntity.copy(
                        addedTime = cart.addedCartInstant.toEpochMilli(),
                        ingredients = cart.ingredients
                    )
                    db.changeCartItem(updateCart)
                } else {
                    db.insertCartItem(cart.toEntityModel())
                }
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

    override suspend fun removeIngredientInCartItem(cartItem: Cart, ingredientName: String) {
        try {
            withContext(Dispatchers.IO) {
                val newIngredients = cartItem.ingredients.filter { it.name != ingredientName }
                if (newIngredients.isEmpty()) {
                    db.removeCartItem(cartItem.toEntityModel())
                } else {
                    val updateCartItem = cartItem.copy(ingredients = newIngredients)
                    db.changeCartItem(updateCartItem.toEntityModel())
                }
            }
        } catch (e: Exception) {
            //
        }
    }

    override suspend fun removeMenuInCartItem(cartItem: Cart) {
        try {
            withContext(Dispatchers.IO) {
                db.removeCartItem(cartItem.toEntityModel())
            }
        } catch (e: Exception) {
            //
        }
    }

    override suspend fun changeQuantityOfCartItems(quantityMap: Map<CartKey, Int>): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                val groupKey = quantityMap.entries.groupBy { it.key.menuName }

                for ((menuName, ingredientEntries) in groupKey) {
                    val cartEntity = findCartItem(menuName)
                    if (cartEntity != null) {
                        val updateIngredients = cartEntity.ingredients.map { ingredient ->
                            val matched = ingredientEntries.find { it.key.ingredientName == ingredient.name }
                            if (matched != null) {
                                ingredient.copy(cartQuantity = matched.value)
                            } else {
                                ingredient
                            }
                        }

                        val updateCart = cartEntity.copy(ingredients = updateIngredients)
                        db.changeCartItem(updateCart)
                    }
                }
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateOrderCartItem(cartItems: List<Cart>) {
        try {
            withContext(Dispatchers.IO) {
                for (item in cartItems) {
                    val updateCartItem = item.copy(isOrdered = true)
                    db.changeCartItem(updateCartItem.toEntityModel())
                }
            }
        } catch (e: Exception) {
            //
        }
    }

    private suspend fun findCartItem(menuName: String): CartEntity? {
        return try {
            withContext(Dispatchers.IO) {
                db.queryCartItem(menuName)
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun Cart.toEntityModel(): CartEntity {
        return CartEntity(
            id = id ?: 0,
            addedTime = addedCartInstant.toEpochMilli(),
            categoryName = categoryType.categoryName,
            menuName = menuName,
            menuImageUrl = menuImageUrl,
            ingredients = ingredients,
            isOrdered = isOrdered
        )
    }

    private fun CartEntity.toDomainModel(): Cart {
        return Cart(
            id = id,
            addedCartInstant = Instant.ofEpochMilli(addedTime),
            categoryType = CategoryType.findByFirebaseDoc(categoryName),
            menuName = menuName,
            menuImageUrl = menuImageUrl,
            ingredients = ingredients,
            isOrdered = isOrdered
        )
    }
}
