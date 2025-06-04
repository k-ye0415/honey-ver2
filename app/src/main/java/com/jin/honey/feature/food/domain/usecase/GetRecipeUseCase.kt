package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository

class GetRecipeUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(menuName: String) {

    }
}
