package com.jin.honey.feature.food.data.model

import androidx.room.Entity
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.RecipeStep

@Entity(tableName = "food", primaryKeys = ["menuName"])
data class FoodEntity(
    val categoryName: String,
    val menuName: String,
    val imageUrl: String,
    val cookingTime: String,
    val recipeStep: List<RecipeStep>,
    val ingredients: List<Ingredient>
)
