package com.jin.honey.feature.food.data

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.jin.honey.feature.food.domain.model.Ingredient
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
}
