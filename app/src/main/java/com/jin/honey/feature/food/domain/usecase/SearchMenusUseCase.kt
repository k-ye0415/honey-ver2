package com.jin.honey.feature.food.domain.usecase

import com.jin.domain.FoodRepository
import com.jin.model.food.MenuPreview

class SearchMenusUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(keyword: String): Result<List<MenuPreview>> {
        val searchMenus = repository.searchMenuByKeyword(keyword)
        return if (searchMenus.isEmpty()) Result.failure(Exception("Serch menu is empty"))
        else Result.success(searchMenus)
    }
}
