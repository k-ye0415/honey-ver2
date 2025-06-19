package com.jin.honey.feature.recipe.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeTrackingDataSource {
    @Insert
    suspend fun insertDefaultRecipe(entity: RecipeEntity)

    @Query("SELECT * FROM recipe")
    suspend fun queryRecipeList(): List<RecipeEntity>
}
