package com.jin.honey.feature.recipe.data

import com.jin.honey.feature.food.data.FoodTrackingDataSource
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Recipe
import com.jin.honey.feature.recipe.domain.RecipeRepository
import com.jin.honey.feature.recipe.domain.model.RecipePreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(private val db: FoodTrackingDataSource) : RecipeRepository {
    override suspend fun fetchRecommendRecipe(): Result<List<RecipePreview>> {
        return try {
            withContext(Dispatchers.IO) {
                val entities = db.queryRecipeList().shuffled().take(10)
                val recipePreviews = entities.map {
                    RecipePreview(
                        categoryType = CategoryType.findByFirebaseDoc(it.categoryName),
                        menuName = it.menuName,
                        menuImageUrl = it.imageUrl,
                        recipe = Recipe(cookingTime = it.cookingTime, recipeSteps = it.recipeStep)
                    )
                }
                Result.success(recipePreviews)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
