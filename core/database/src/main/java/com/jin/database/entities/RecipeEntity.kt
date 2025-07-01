package com.jin.database.entities

import androidx.room.Entity
import com.jin.domain.recipe.model.RecipeStep

@Entity(tableName = "recipe", primaryKeys = ["type", "menuName"])
data class RecipeEntity(
    val type: String,
    val menuName: String,
    val cookingTime: String,
    val recipeStep: List<RecipeStep>,
)
