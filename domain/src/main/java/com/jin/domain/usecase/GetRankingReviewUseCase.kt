package com.jin.domain.usecase

import com.jin.domain.food.FoodRepository
import com.jin.domain.review.ReviewRepository
import com.jin.domain.food.model.CategoryType
import com.jin.domain.review.ReviewRankPreview

class GetRankingReviewUseCase(
    private val reviewRepository: ReviewRepository,
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(): Result<List<ReviewRankPreview>> {
        val categoryTypes = listOf(
            CategoryType.Burger,
            CategoryType.Western,
            CategoryType.Chinese,
            CategoryType.Japanese,
            CategoryType.Korean,
            CategoryType.Snack,
            CategoryType.Vegan,
            CategoryType.Dessert
        )

        val reviewRankings = categoryTypes.mapNotNull { category ->
            val reviews = reviewRepository.fetchReviewByCategory(category.categoryName)
            val topReview = reviews.maxByOrNull { it.reviewContent.totalScore } ?: return@mapNotNull null
            val reviewsOfTopMenu = reviews.filter { it.menuName == topReview.menuName }

            val imageUrl = foodRepository.fetchMenuImage(topReview.menuName)

            val reviewCount = reviewsOfTopMenu.size
            val averageScore = if (reviewCount > 0) {
                reviewsOfTopMenu.sumOf { it.reviewContent.totalScore } / reviewCount
            } else {
                0.0
            }

            ReviewRankPreview(
                categoryType = category,
                menuName = topReview.menuName,
                menuImageUrl = imageUrl,
                reviewScore = averageScore,
                reviewCount = reviewCount
            )
        }

        return if (reviewRankings.isNotEmpty())
            Result.success(reviewRankings)
        else
            Result.failure(Exception("No ranking reviews available"))
    }
}
