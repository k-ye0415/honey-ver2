package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.recipe.domain.RecipeRepository
import com.jin.honey.feature.recipe.domain.model.RecipePreview

class GetRecipeUseCase(
    private val recipeRepository: RecipeRepository,
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(menuName: String): Result<RecipePreview> {
        val recipe = recipeRepository.findRecipeByMenuName(menuName)
            ?: return Result.failure(Exception("Recipe is null"))
        val menu = foodRepository.findMenuByMenuName(recipe.menuName)
            ?: return Result.failure(Exception("Menu is null"))
        val recipePreview = RecipePreview(
            categoryType = menu.type,
            menuName = menuName,
            menuImageUrl = menu.menuImageUrl,
            recipe = recipe
        )
        return Result.success(recipePreview)
    }
}
