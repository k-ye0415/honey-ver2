package com.jin.honey.feature.recipe.data

import android.util.Log
import com.jin.database.datasource.RecipeTrackingDataSource
import com.jin.database.entities.RecipeEntity
import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.recipe.domain.RecipeRepository
import com.jin.model.recipe.Recipe
import com.jin.model.recipe.RecipeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
    private val db: RecipeTrackingDataSource,
    private val fireStoreDataSource: FireStoreDataSource
) : RecipeRepository {

    override suspend fun syncRecipe() {
        fireStoreDataSource.fetchAllRecipeWithMenus()
            .onSuccess { defaultRecipeSave(it) }
            .onFailure { Log.e(TAG, "sync recipe is fail\n${it.printStackTrace()}") }
    }

    override suspend fun fetchRecommendRecipe(): List< Recipe> = try {
        withContext(Dispatchers.IO) {
            val entities = db.queryRecipeList().shuffled().take(10)
            entities.map { it.toDomain() }
        }
    } catch (e: Exception) {
        emptyList()
    }

    override suspend fun findRecipeByMenuName(menuName: String):  Recipe? = try {
        withContext(Dispatchers.IO) {
            val entity = db.queryRecipeByMenuName(menuName)
            entity.toDomain()
        }
    } catch (e: Exception) {
        null
    }

    private suspend fun defaultRecipeSave(recipes: List< Recipe>) {
        try {
            withContext(Dispatchers.IO) {
                for (recipe in recipes) {
                    db.insertDefaultRecipe(recipe.toEntity())
                }
            }
        } catch (e: Exception) {
            //
        }
    }

    private fun  Recipe.toEntity(): RecipeEntity {
        return RecipeEntity(type = type.type, menuName = menuName, cookingTime = cookingTime, recipeStep = recipeSteps)
    }

    private fun RecipeEntity.toDomain():  Recipe {
        return  Recipe(
            type =  RecipeType.findByTypName(type),
            menuName = menuName,
            cookingTime = cookingTime,
            recipeSteps = recipeStep
        )
    }

    private companion object {
        val TAG = "RecipeRepository"
    }
}
