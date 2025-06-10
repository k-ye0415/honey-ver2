package com.jin.honey.feature.favorite.domain

import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.MenuPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetRecentlyMenuUseCase(
    private val preferencesRepository: PreferencesRepository,
    private val foodRepository: FoodRepository
) {
    operator fun invoke(): Flow<Result<List<MenuPreview>>> {
        return preferencesRepository.flowRecentlyMenus()
            .map { menuList ->
                val menuPreviewList = menuList.mapNotNull { menuName ->
                    foodRepository.findMenu(menuName).getOrNull()
                }
                Result.success(menuPreviewList)
            }
            .catch { e -> emit(Result.failure(e)) } // 예외 처리
    }
}
