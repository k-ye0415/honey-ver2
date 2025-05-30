package com.jin.honey.feature.food.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jin.honey.feature.food.domain.model.Ingredient

@Entity(tableName = "food")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryName: String,
    val menuName: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>
)
