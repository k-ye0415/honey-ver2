package com.jin.honey.feature.food.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.honey.feature.food.data.model.CategoryEntity

@Dao
interface FoodTrackingDataSource {
    @Insert
    suspend fun insertOrUpdateCategory(categoryEntity: CategoryEntity)

    @Query("SELECT categoryName FROM category")
    suspend fun getCategoryNames(): List<String>
}
