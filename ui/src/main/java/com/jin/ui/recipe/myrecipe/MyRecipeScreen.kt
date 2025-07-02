package com.jin.ui.recipe.myrecipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jin.domain.recipe.model.Recipe
import com.jin.domain.recipe.model.RecipeStep
import com.jin.domain.recipe.model.RecipeType
import com.jin.ui.recipe.myrecipe.content.MyRecipeHeader
import com.jin.ui.recipe.myrecipe.content.MyRecipeSaveButton
import com.jin.ui.recipe.myrecipe.content.MyRecipeSteps
import com.jin.ui.theme.HoneyTheme

@Composable
fun MyRecipeScreen(viewModel: MyRecipeViewModel, menuName: String) {
    val recipeStepList = mutableListOf<RecipeStep>()
    val firstRecipeStep = RecipeStep(step = 1, title = "", description = listOf())
    recipeStepList.add(firstRecipeStep)
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            MyRecipeHeader(menuName)
            MyRecipeSteps(modifier = Modifier.weight(1f), recipeStepList = recipeStepList)
            MyRecipeSaveButton()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewContent() {
    HoneyTheme {
        MyRecipeScreen(MyRecipeViewModel(), "치즈버거")
    }
}

val recipeFallback = Recipe(
    type = RecipeType.MY_OWN, menuName = "치즈버거", cookingTime = "총 조리시간", recipeSteps = listOf(
        RecipeStep(1, title = "ridens", description = listOf())
    )
)
