package com.jin.honey.feature.recipe.data

import com.jin.honey.feature.food.data.FoodTrackingDataSource
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.recipe.domain.RecipeRepository
import com.jin.honey.feature.recipe.domain.model.Recipe
import com.jin.honey.feature.recipe.domain.model.RecipePreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(private val db: FoodTrackingDataSource) : RecipeRepository {
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
}
