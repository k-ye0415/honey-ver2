package com.jin.honey.feature.food.data

import android.util.Log
import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.data.model.FoodEntity
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.food.domain.model.Recipe
import com.jin.honey.feature.ingredient.model.IngredientPreview
import com.jin.honey.feature.recipe.domain.model.RecipePreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepositoryImpl(
    private val db: FoodTrackingDataSource,
    private val fireStoreDataSource: FireStoreDataSource
) : FoodRepository {

    override suspend fun syncAllMenu() {
        fireStoreDataSource.fetchAllCategoriesWithMenus()
            .onSuccess { insertOrUpdateAllCategoriesAndMenus(it) }
            .onFailure { Log.e(TAG, "syncAllMenu is Fail\n${it.printStackTrace()}") }
    }

    override suspend fun findCategories(): Result<List<String>> {
        return try {
            withContext(Dispatchers.IO) {
                val entities = db.queryCategoriesNames()
                val categoryTypeList = entities.toSet().toList()
                Result.success(categoryTypeList)
            }
        } catch (e: Exception) {
            Log.e(TAG, "findCategories is Fail\n${e.printStackTrace()}")
            Result.failure(e)
        }
    }

    override suspend fun findAllCategoriesAndMenus(): Result<List<Food>> =
        try {
            val entities = db.queryAllCategoriesAndMenus()
            val categories = entities.toDomainModel()
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun findIngredientByMenuName(menuName: String): Result<IngredientPreview> {
        return try {
            withContext(Dispatchers.IO) {
                val entity = db.queryMenuByMenuName(menuName)
                val ingredientPreview = IngredientPreview(
                    categoryType = CategoryType.findByFirebaseDoc(entity.categoryName),
                    menuName = entity.menuName,
                    imageUrl = entity.imageUrl,
                    ingredients = entity.ingredients
                )
                Result.success(ingredientPreview)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun findRecipeByMenuName(menuName: String): Result<RecipePreview> {
        return try {
            withContext(Dispatchers.IO) {
                val entity = db.queryRecipeByMenuName(menuName)
                val recipePreview = RecipePreview(
                    categoryType = CategoryType.findByFirebaseDoc(entity.categoryName),
                    menuName = entity.menuName,
                    menuImageUrl = entity.imageUrl,
                    recipe = Recipe(cookingTime = entity.cookingTime, recipeSteps = entity.recipeStep)
                )
                Result.success(recipePreview)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun findRandomMenus(): Result<List<MenuPreview>> {
        return try {
            withContext(Dispatchers.IO) {
                val entity = db.queryMenus().shuffled().take(10)
                val menuList = entity.map {
                    MenuPreview(
                        CategoryType.findByFirebaseDoc(it.categoryName),
                        it.menuName,
                        it.imageUrl
                    )
                }
                Result.success(menuList)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchMenuByKeyword(keyword: String): Result<List<MenuPreview>> {
        return try {
            withContext(Dispatchers.IO) {
                val query = "%$keyword%"
                val entity = db.queryMenusByKeyword(query)
                val menuList = entity.map {
                    MenuPreview(
                        CategoryType.findByFirebaseDoc(it.categoryName),
                        it.menuName,
                        it.imageUrl
                    )
                }
                Result.success(menuList)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun findMenu(menuName: String): Result<MenuPreview> {
        return try {
            withContext(Dispatchers.IO) {
                val entity = db.queryMenusByMenuName(menuName)
                val menuPreview = MenuPreview(
                    CategoryType.findByFirebaseDoc(entity.categoryName),
                    entity.menuName,
                    entity.imageUrl
                )
                Result.success(menuPreview)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchMenuImage(menuName: String): Result<String> {
        return try {
            val imageUrl = db.queryMenuImageUrl(menuName)
            Result.success(imageUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun insertOrUpdateAllCategoriesAndMenus(list: List<Food>) {
        try {
            withContext(Dispatchers.IO) {
                val entities = list.flatMap { it.toEntityModel() }
                for (entity in entities) {
                    db.insertOrUpdateAllFood(entity)
                }
            }
        } catch (e: Exception) {
            // Silently ignore the error.
        }
    }


    private fun Food.toEntityModel(): List<FoodEntity> {
        return menu.map {
            FoodEntity(
                categoryName = categoryType.categoryName,
                menuName = it.name,
                imageUrl = it.imageUrl,
                cookingTime = it.recipe.cookingTime,
                recipeStep = it.recipe.recipeSteps,
                ingredients = it.ingredient
            )
        }
    }

    private fun List<FoodEntity>.toDomainModel(): List<Food> {
        return this.groupBy { it.categoryName }.map { (categoryName, entities) ->
            Food(
                categoryType = CategoryType.findByFirebaseDoc(categoryName),
                menu = entities.map {
                    Menu(
                        name = it.menuName,
                        imageUrl = it.imageUrl,
                        recipe = Recipe(cookingTime = it.cookingTime, recipeSteps = it.recipeStep),
                        ingredient = it.ingredients
                    )
                }
            )
        }
    }

    private companion object {
        val TAG = "FoodRepository"
    }
}
