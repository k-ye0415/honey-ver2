package com.jin.honey.feature.food.data.model

import androidx.room.Entity
import com.jin.honey.feature.food.domain.model.Ingredient

@Entity(tableName = "food", primaryKeys = ["menuName"])
data class FoodEntity(
    val categoryName: String,
    val menuName: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>
)
