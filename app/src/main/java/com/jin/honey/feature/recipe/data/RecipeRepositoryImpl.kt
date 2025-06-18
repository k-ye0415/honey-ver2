package com.jin.honey.feature.recipe.data

import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.data.FoodTrackingDataSource
import com.jin.honey.feature.recipe.domain.RecipeRepository
import com.jin.honey.feature.recipe.domain.model.RecipePreview

class RecipeRepositoryImpl(
    private val db: FoodTrackingDataSource,
    private val fireStoreDataSource: FireStoreDataSource
) : RecipeRepository {
    override suspend fun fetchRecommendRecipe(): List<RecipePreview> {
        return try {
            fireStoreDataSource.fetchAllRecipeWithMenus()
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
