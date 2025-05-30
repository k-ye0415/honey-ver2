package com.jin.honey.feature.food.data

import android.util.Log
import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.data.model.CategoryEntity
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Menu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepositoryImpl(
    private val db: FoodTrackingDataSource,
    private val fireStoreDataSource: FireStoreDataSource
) : FoodRepository {

    override suspend fun syncAllMenu() {
        fireStoreDataSource.requestAllCategoryMenus()
            .onSuccess { insertOrUpdateAllCategoryMenus(it) }
            .onFailure { Log.e(TAG, "syncAllMenu is Fail\n${it.printStackTrace()}") }
    }

    override suspend fun findCategories(): Result<List<String>> {
        return try {
            withContext(Dispatchers.IO) {
                val nameList = db.getCategoryNames()
                val categoryTypes = nameList.toSet().toList()
                Result.success(categoryTypes)
            }
        } catch (e: Exception) {
            Log.e(TAG, "findCategories is Fail\n${e.printStackTrace()}")
            Result.failure(e)
        }
    }

    override suspend fun findAllCategoryMenus(): Result<List<Category>> =
        try {
            val allData = db.getAllCategoryAndMenus()
            val categories = allData.toDomainModel()
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun findMenuIngredient(menuName: String): Result<Menu> {
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

    private suspend fun insertOrUpdateAllCategoryMenus(list: List<Category>) {
        try {
            withContext(Dispatchers.IO) {
                val entities = list.flatMap { it.toEntityModel() }
                for (entity in entities) {
                    db.insertOrUpdateCategory(entity)
                }
            }
        } catch (e: Exception) {
            // Silently ignore the error.
        }
    }


    private fun Category.toEntityModel(): List<CategoryEntity> {
        return menu.map {
            CategoryEntity(
                categoryName = categoryType.categoryName,
                menuName = it.name,
                imageUrl = it.imageUrl,
                ingredients = it.ingredient
            )
        }
    }

    private fun List<CategoryEntity>.toDomainModel(): List<Category> {
        return this.groupBy { it.categoryName }.map { (categoryName, entities) ->
            Category(
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
