package com.jin.database

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.jin.domain.address.model.Address
import com.jin.domain.cart.model.Cart
import com.jin.domain.cart.model.IngredientCart
import com.jin.domain.food.model.Ingredient
import com.jin.domain.recipe.model.RecipeStep
import com.jin.network.NetworkProvider

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

    @TypeConverter
    fun cartListToJsonString(value: List<Cart>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonStringToCartList(value: String): List<Cart> {
        val type = object : TypeToken<List<Cart>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun userAddressToJsonString(value: Address): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonStringToUserAddress(value: String): Address {
        val type = object : TypeToken<Address>() {}.type
        return gson.fromJson(value, type)
    }
}
