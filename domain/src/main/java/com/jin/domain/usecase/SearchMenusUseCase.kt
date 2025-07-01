package com.jin.domain.usecase

import com.jin.domain.food.FoodRepository
import com.jin.domain.food.model.MenuPreview

class SearchMenusUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(keyword: String): Result<List<MenuPreview>> {
        val searchMenus = repository.searchMenuByKeyword(keyword)
        return if (searchMenus.isEmpty()) Result.failure(Exception("Serch menu is empty"))
        else Result.success(searchMenus)
    }
}
