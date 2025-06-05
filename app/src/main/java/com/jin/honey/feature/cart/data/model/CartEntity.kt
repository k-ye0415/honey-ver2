package com.jin.honey.feature.cart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jin.honey.feature.cart.domain.model.IngredientCart

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val addedTime: Long,
    val menuName: String,
    val menuImageUrl: String,
    val ingredients: List<IngredientCart>,
    val isOrdered: Boolean
)
