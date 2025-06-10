package com.jin.honey.feature.favorite.domain

import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.MenuPreview
import kotlinx.coroutines.flow.first

class GetFavoriteMenuUseCase(
    private val preferencesRepository: PreferencesRepository,
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(): Result<List<MenuPreview>> {
        return try {
            val menuList = preferencesRepository.getFavoriteMenus().first() // ✅ 첫 값만 가져옴
            val menuPreviewList = menuList.mapNotNull { menuName ->
                foodRepository.findMenu(menuName).getOrNull() // 실패한 건 제외
            }
            Result.success(menuPreviewList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
