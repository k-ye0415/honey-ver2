package com.jin.honey.feature.food.data

import android.util.Log
import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.data.model.FoodEntity
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Menu
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

    override suspend fun findIngredientAt(menuName: String): Result<Menu> {
        return try {
            withContext(Dispatchers.IO) {
                val entity = db.getMenuIngredient(menuName)
                val menu = Menu(name = entity.menuName, imageUrl = entity.imageUrl, ingredient = entity.ingredients)
                Result.success(menu)
            }
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
