package com.jin.honey.feature.food.domain.usecase

import com.jin.domain.FoodRepository

class SyncAllMenuUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke() {
        return repository.syncAllMenu()
    }
}
