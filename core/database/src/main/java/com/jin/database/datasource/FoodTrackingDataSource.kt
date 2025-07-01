package com.jin.database.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jin.database.entities.FoodEntity
import com.jin.database.entities.IngredientEntity
import com.jin.database.entities.MenuEntity

@Dao
interface FoodTrackingDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAllFood(foodEntity: FoodEntity)

    @Query("SELECT * FROM food")
    suspend fun queryAllCategoriesAndMenus(): List<FoodEntity>

    @Query("SELECT categoryName FROM food")
    suspend fun queryCategoriesNames(): List<String>

    @Query("SELECT categoryName, menuName, imageUrl, ingredients FROM food WHERE menuName = :menuName")
    suspend fun queryMenuByMenuName(menuName: String): IngredientEntity

    @Query("SELECT categoryName, menuName, imageUrl FROM food")
    suspend fun queryMenus(): List<MenuEntity>

    @Query("SELECT categoryName, menuName, imageUrl FROM food WHERE menuName = :menuName")
    suspend fun queryMenusByMenuName(menuName: String): MenuEntity

    @Query("SELECT categoryName, menuName, imageUrl FROM food WHERE menuName LIKE :keyword")
    suspend fun queryMenusByKeyword(keyword: String): List<MenuEntity>

    @Query("SELECT imageUrl FROM food WHERE menuName = :menuName")
    suspend fun queryMenuImageUrl(menuName: String): String
}
