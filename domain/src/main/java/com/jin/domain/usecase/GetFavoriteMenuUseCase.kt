package com.jin.domain.usecase

import com.jin.domain.favorite.FavoritePreview
import com.jin.domain.food.FoodRepository
import com.jin.domain.favorite.FavoriteRepository
import com.jin.domain.review.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetFavoriteMenuUseCase(
    private val favoriteRepository: FavoriteRepository,
    private val foodRepository: FoodRepository,
    private val reviewRepository: ReviewRepository,
) {
    operator fun invoke(): Flow<Result<List<FavoritePreview>>> {
        return favoriteRepository.flowFavoriteMenus()
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
            .catch { e -> emit(Result.failure(e)) }
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
