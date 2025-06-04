package com.jin.honey.feature.cart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jin.honey.feature.food.domain.model.Ingredient

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val addedTime: Long,
    val menuName: String,
    val menuImageUrl: String,
    val quantity: Int,
    val ingredients: List<Ingredient>,
    val isOrdered: Boolean
)
