package com.jin.honey.feature.food.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jin.honey.feature.food.data.model.FoodEntity
import com.jin.honey.feature.food.data.model.MenuEntity

@Dao
interface FoodTrackingDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAllFood(foodEntity: FoodEntity)

    @Query("SELECT * FROM food")
    suspend fun queryAllCategoriesAndMenus(): List<FoodEntity>

    @Query("SELECT categoryName FROM food")
    suspend fun queryCategoriesNames(): List<String>

    @Query("SELECT menuName, imageUrl, cookingTime, recipeStep, ingredients FROM food WHERE menuName = :menuName")
    suspend fun getMenuIngredient(menuName: String): MenuEntity
}
