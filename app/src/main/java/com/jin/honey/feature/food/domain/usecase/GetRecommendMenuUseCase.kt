package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.MenuPreview

class GetRecommendMenuUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(): Result<List<MenuPreview>> {
        return repository.findRandomMenus()
    }
}
