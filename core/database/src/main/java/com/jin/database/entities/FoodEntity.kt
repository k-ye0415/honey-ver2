package com.jin.database.entities

import androidx.room.Entity
import com.jin.domain.food.model.Ingredient

@Entity(tableName = "food", primaryKeys = ["menuName"])
data class FoodEntity(
    val categoryName: String,
    val menuName: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>
)
