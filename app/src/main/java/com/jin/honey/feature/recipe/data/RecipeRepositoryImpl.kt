package com.jin.honey.feature.recipe.data

import android.util.Log
import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.data.FoodTrackingDataSource
import com.jin.honey.feature.recipe.domain.RecipeRepository
import com.jin.honey.feature.recipe.domain.model.Recipe
import com.jin.honey.feature.recipe.domain.model.RecipePreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
    private val db: FoodTrackingDataSource,
    private val recipeDb: RecipeTrackingDataSource,
    private val fireStoreDataSource: FireStoreDataSource
) : RecipeRepository {

    override suspend fun syncRecipe() {
        fireStoreDataSource.fetchAllRecipeWithMenus()
            .onSuccess { defaultRecipeSave(it) }
            .onFailure { Log.e(TAG, "sync recipe is fail\n${it.printStackTrace()}") }
    }

    override suspend fun fetchRecommendRecipe(): List<RecipePreview> {
        return try {
            emptyList()
//            withContext(Dispatchers.IO) {
//                val entities = db.queryRecipeList().shuffled().take(10)
//                entities.map {
//                    RecipePreview(
//                        categoryType = CategoryType.findByFirebaseDoc(it.categoryName),
//                        menuName = it.menuName,
//                        menuImageUrl = it.imageUrl,
//                        recipe = Recipe(cookingTime = it.cookingTime, recipeSteps = it.recipeStep)
//                    )
//                }
//            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun defaultRecipeSave(recipes: List<Recipe>) {
        try {
            withContext(Dispatchers.IO) {
                for (recipe in recipes) {
                    recipeDb.insertDefaultRecipe(recipe.toEntity())
                }
            }
        } catch (e: Exception) {
            //
        }
    }

    private fun Recipe.toEntity(): RecipeEntity {
        return RecipeEntity(type = type.type, menuName = menuName, cookingTime = cookingTime, recipeStep = recipeSteps)
    }

    private companion object {
        val TAG = "RecipeRepository"
    }
}
