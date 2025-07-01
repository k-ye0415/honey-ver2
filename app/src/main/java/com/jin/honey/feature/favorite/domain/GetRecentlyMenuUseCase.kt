package com.jin.honey.feature.favorite.domain

import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.review.domain.ReviewRepository
import com.jin.model.favorite.FavoritePreview
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
                    val previews = menuNames.map { menuName ->
                        buildFavoritePreview(menuName)
                    }
                    Result.success(previews)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
            .catch { e -> emit(Result.failure(e)) } // 예외 처리
    }

    private suspend fun buildFavoritePreview(menuName: String): FavoritePreview {
        val menu = foodRepository.findMenuByMenuName(menuName)
        val reviews = reviewRepository.fetchMenuReview(menuName)

        val scoreSum = reviews.sumOf { it.reviewContent.totalScore }
        val reviewCount = reviews.size

        return FavoritePreview(
            menuName = menu?.menuName.orEmpty(),
            imageUrl = menu?.menuImageUrl.orEmpty(),
            reviewScore = if (reviewCount == 0) 0.0 else scoreSum / reviewCount,
            reviewCount = reviewCount
        )
    }
}
