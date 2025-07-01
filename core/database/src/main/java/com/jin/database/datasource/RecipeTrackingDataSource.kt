package com.jin.database.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.database.entities.RecipeEntity

@Dao
interface RecipeTrackingDataSource {
    @Insert
    suspend fun insertDefaultRecipe(entity: RecipeEntity)

    @Query("SELECT * FROM recipe")
    suspend fun queryRecipeList(): List<RecipeEntity>

    @Query("SELECT * FROM recipe WHERE menuName = :menuName")
    suspend fun queryRecipeByMenuName(menuName: String): RecipeEntity
}
