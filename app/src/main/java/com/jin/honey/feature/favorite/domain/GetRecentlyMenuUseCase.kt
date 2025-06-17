package com.jin.honey.feature.favorite.domain

import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.review.domain.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetRecentlyMenuUseCase(
    private val preferencesRepository: PreferencesRepository,
    private val foodRepository: FoodRepository,
    private val reviewRepository: ReviewRepository
) {
    operator fun invoke(): Flow<Result<List<FavoritePreview>>> {
        return preferencesRepository.flowRecentlyMenus()
            .map { menuNames ->
                try {
                    val previews = menuNames.mapNotNull { menuName ->
                        buildFavoritePreview(menuName)
                    }
                    Result.success(previews)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
            .catch { e -> emit(Result.failure(e)) } // 예외 처리
    }

    private suspend fun buildFavoritePreview(menuName: String): FavoritePreview? {
        val menuResult = foodRepository.findMenu(menuName)
        val reviewResult = reviewRepository.fetchMenuReview(menuName)

//        if (menuResult.isFailure || reviewResult.isFailure) return null

        val menu = menuResult.getOrNull()!!
//        val reviews = reviewResult.getOrNull() ?: emptyList()

        val scoreSum = reviewResult.sumOf { it.reviewContent.totalScore }
        val reviewCount = reviewResult.size

        return FavoritePreview(
            menuName = menu.menuName,
            imageUrl = menu.menuImageUrl,
            reviewScore = if (reviewCount == 0) 0.0 else scoreSum / reviewCount,
            reviewCount = reviewCount
        )
    }
}
