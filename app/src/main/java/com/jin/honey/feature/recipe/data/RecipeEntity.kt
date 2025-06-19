package com.jin.honey.feature.recipe.data

import androidx.room.Entity
import com.jin.honey.feature.recipe.domain.model.RecipeStep

@Entity(tableName = "recipe", primaryKeys = ["type", "menuName"])
data class RecipeEntity(
    val type: String,
    val menuName: String,
    val cookingTime: String,
    val recipeStep: List<RecipeStep>,
)
