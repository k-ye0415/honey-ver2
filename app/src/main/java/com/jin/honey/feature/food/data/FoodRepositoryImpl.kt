package com.jin.honey.feature.food.data

import android.util.Log
import com.jin.database.datasource.FoodTrackingDataSource
import com.jin.database.entities.FoodEntity
import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.domain.repositories.FoodRepository
import com.jin.domain.model.food.CategoryType
import com.jin.domain.model.food.Food
import com.jin.domain.model.food.IngredientPreview
import com.jin.domain.model.food.Menu
import com.jin.domain.model.food.MenuPreview
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

    override suspend fun findCategoryNames(): List<String> = try {
        withContext(Dispatchers.IO) {
            db.queryCategoriesNames().toSet().toList()
        }
    } catch (e: Exception) {
        Log.e(TAG, "findCategories is Fail\n${e.printStackTrace()}")
        emptyList()
    }


    override suspend fun fetchAllFoodList(): List< Food> =
        try {
            val entities = db.queryAllCategoriesAndMenus()
            entities.toDomainModel()
        } catch (e: Exception) {
            emptyList()
        }

    override suspend fun findIngredientByMenuName(menuName: String):  com.jin.domain.model.food.IngredientPreview? = try {
        withContext(Dispatchers.IO) {
            val entity = db.queryMenuByMenuName(menuName)
            com.jin.domain.model.food.IngredientPreview(
                categoryType = CategoryType.findByFirebaseDoc(entity.categoryName),
                menuName = entity.menuName,
                imageUrl = entity.imageUrl,
                ingredients = entity.ingredients
            )
        }
    } catch (e: Exception) {
        null
    }

    override suspend fun fetchRandomMenus(): List< MenuPreview> = try {
        withContext(Dispatchers.IO) {
            val entity = db.queryMenus().shuffled().take(10)
            entity.map {
                 MenuPreview(
                     CategoryType.findByFirebaseDoc(it.categoryName),
                    it.menuName,
                    it.imageUrl
                )
            }
        }
    } catch (e: Exception) {
        emptyList()
    }

    override suspend fun searchMenuByKeyword(keyword: String): List< MenuPreview> = try {
        withContext(Dispatchers.IO) {
            val query = "%$keyword%"
            val entity = db.queryMenusByKeyword(query)
            return@withContext entity.map {
                 MenuPreview(
                     CategoryType.findByFirebaseDoc(it.categoryName),
                    it.menuName,
                    it.imageUrl
                )
            }
        }
    } catch (e: Exception) {
        emptyList()
    }

    override suspend fun findMenuByMenuName(menuName: String):  MenuPreview? = try {
        withContext(Dispatchers.IO) {
            val entity = db.queryMenusByMenuName(menuName)
             MenuPreview(
                 CategoryType.findByFirebaseDoc(entity.categoryName),
                entity.menuName,
                entity.imageUrl
            )
        }
    } catch (e: Exception) {
        null
    }

    override suspend fun fetchMenuImage(menuName: String): String = try {
        db.queryMenuImageUrl(menuName)
    } catch (e: Exception) {
        ""
    }

    private suspend fun insertOrUpdateAllCategoriesAndMenus(list: List< Food>) {
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
//                cookingTime = it.recipe.cookingTime,
//                recipeStep = it.recipe.recipeSteps,
                ingredients = it.ingredient
            )
        }
    }

    private fun List<FoodEntity>.toDomainModel(): List< Food> {
        return this.groupBy { it.categoryName }.map { (categoryName, entities) ->
             Food(
                categoryType =  CategoryType.findByFirebaseDoc(categoryName),
                menu = entities.map {
                     Menu(
                        name = it.menuName,
                        imageUrl = it.imageUrl,
//                        recipe = Recipe(cookingTime = it.cookingTime, recipeSteps = it.recipeStep),
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
