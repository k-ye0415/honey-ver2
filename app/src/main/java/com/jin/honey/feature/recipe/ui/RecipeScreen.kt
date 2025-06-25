package com.jin.honey.feature.recipe.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.jin.honey.feature.recipe.domain.model.RecipePreview
import com.jin.honey.feature.recipe.ui.content.MyRecipe
import com.jin.honey.feature.recipe.ui.content.RecipeContent
import com.jin.honey.feature.recipe.ui.content.RecipeHeader
import com.jin.honey.feature.recipe.ui.content.RecipeOverview
import com.jin.honey.feature.ui.state.UiState

@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel,
    menuName: String,
    onNavigateToBack: () -> Unit,
    onNavigateToChatBot: () -> Unit
) {
    val recipeState by viewModel.recipe.collectAsState()

    LaunchedEffect(menuName) {
        viewModel.findRecipeByMenuName(menuName)
    }

    when (val state = recipeState) {
        is UiState.Loading -> RecipeSuccessScreen(null, onNavigateToBack, onNavigateToChatBot)
        is UiState.Success -> RecipeSuccessScreen(state.data, onNavigateToBack, onNavigateToChatBot)
        is UiState.Error -> RecipeErrorScreen(onNavigateToBack)
    }
}

@Composable
private fun RecipeSuccessScreen(recipe: RecipePreview?, onNavigateToBack: () -> Unit, onNavigateToChatBot: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item { RecipeHeader(onNavigateToBack) }
            if (recipe != null) {
                item {
                    RecipeOverview(
                        imageUrl = recipe.menuImageUrl,
                        menuName = recipe.menuName,
                        cookingTime = recipe.recipe.cookingTime,
                        onNavigateToChatBot = onNavigateToChatBot
                    )
                }
                item { RecipeContent(recipe.recipe.recipeSteps) }
                item { MyRecipe() }
            } else {
                item { CircularProgressIndicator() }
            }
        }
    }
}

@Composable
private fun RecipeErrorScreen(onNavigateToBack: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            RecipeHeader(onNavigateToBack)
            Text(text = "레시피를 불러올 수 없습니다.")
        }
    }
}
