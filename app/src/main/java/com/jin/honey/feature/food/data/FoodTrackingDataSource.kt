package com.jin.honey.feature.food.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jin.honey.feature.food.data.model.FoodEntity
import com.jin.honey.feature.food.data.model.IngredientEntity
import com.jin.honey.feature.food.data.model.RecipeEntity

@Dao
interface FoodTrackingDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAllFood(foodEntity: FoodEntity)

    @Query("SELECT * FROM food")
    suspend fun queryAllCategoriesAndMenus(): List<FoodEntity>

    @Query("SELECT categoryName FROM food")
    suspend fun queryCategoriesNames(): List<String>

    @Query("SELECT menuName, imageUrl, ingredients FROM food WHERE menuName = :menuName")
    suspend fun queryMenuByMenuName(menuName: String): IngredientEntity

    @Query("SELECT imageUrl, cookingTime, recipeStep FROM food WHERE menuName = :menuName")
    suspend fun queryRecipeByMenuName(menuName: String): RecipeEntity
}
