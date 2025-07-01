package com.jin.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jin.domain.cart.model.IngredientCart

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val addedTime: Long,
    val categoryName: String,
    val menuName: String,
    val menuImageUrl: String,
    val ingredients: List<IngredientCart>,
    val isOrdered: Boolean
)
