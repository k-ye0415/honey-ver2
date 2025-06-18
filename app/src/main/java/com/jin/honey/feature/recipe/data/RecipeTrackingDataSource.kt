package com.jin.honey.feature.recipe.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface RecipeTrackingDataSource {
    @Insert
    suspend fun syncRecipes(entity: RecipeEntity)
}
