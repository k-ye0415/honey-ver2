package com.jin.honey

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.RecipeStep
import com.jin.honey.feature.network.NetworkProvider

class Converters {
    private val gson = NetworkProvider.gson

    @TypeConverter
    fun ingredientsToJsonString(value: List<Ingredient>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonStringToIngredients(value: String): List<Ingredient> {
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun recipeStepsToJsonString(value: List<RecipeStep>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonStringToRecipeSteps(value: String): List<RecipeStep> {
        val type = object : TypeToken<List<RecipeStep>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun ingredientCartToJsonString(value: List<IngredientCart>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonStringToIngredientCart(value: String): List<IngredientCart> {
        val type = object : TypeToken<List<IngredientCart>>() {}.type
        return gson.fromJson(value, type)
    }
}
