package com.jin.domain.usecase

import com.jin.domain.food.FoodRepository

class SyncAllMenuUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke() {
        return repository.syncAllMenu()
    }
}
