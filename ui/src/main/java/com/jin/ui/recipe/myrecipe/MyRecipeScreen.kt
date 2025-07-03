package com.jin.ui.recipe.myrecipe

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import com.jin.domain.recipe.model.Recipe
import com.jin.domain.recipe.model.RecipeStep
import com.jin.domain.recipe.model.RecipeType
import com.jin.state.DbState
import com.jin.ui.recipe.myrecipe.content.MyRecipeHeader
import com.jin.ui.recipe.myrecipe.content.MyRecipeSaveButton
import com.jin.ui.recipe.myrecipe.content.MyRecipeSteps
import kotlinx.coroutines.launch

@Composable
fun MyRecipeScreen(viewModel: MyRecipeViewModel, menuName: String, onNavigateToBackStack: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var recipeStepList by remember {
        mutableStateOf(
            listOf(
                RecipeStep(
                    step = 1,
                    title = "",
                    description = listOf("")
                )
            )
        )
    }
    var cookTimeHourKeyword by remember { mutableStateOf("") }
    var cookTimeMinKeyword by remember { mutableStateOf("") }
    var stepTitleKeywords by remember { mutableStateOf(listOf("")) }
    var stepDescKeywords by remember { mutableStateOf(listOf(listOf(""))) }

    val cookTimeHourFocusRequester = remember { FocusRequester() }
    val cookTimeMinFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.insertState.collect {
            when (it) {
                is DbState.Success -> {
                    Toast.makeText(
                        context,
                        "레시피 저장 완료",
                        Toast.LENGTH_SHORT
                    ).show()
                    onNavigateToBackStack()
                }

                is DbState.Error -> Toast.makeText(
                    context,
                    "저장 실패. 다시 시도해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            MyRecipeHeader(menuName, onNavigateToBackStack)
            MyRecipeSteps(
                modifier = Modifier.weight(1f),
                recipeStepList = recipeStepList,
                cookTimeHourKeyword = cookTimeHourKeyword,
                cookTimeHourFocusRequester = cookTimeHourFocusRequester,
                cookTimeMinKeyword = cookTimeMinKeyword,
                cookTimeMinFocusRequester = cookTimeMinFocusRequester,
                onHourValueChange = { cookTimeHourKeyword = it },
                onMinValueChange = { cookTimeMinKeyword = it },
                stepTitleKeywords = stepTitleKeywords,
                onTitleChange = { listIndex, newTitle ->
                    stepTitleKeywords = stepTitleKeywords.toMutableList().apply {
                        set(listIndex, newTitle)
                    }
                },
                stepDescKeywords = stepDescKeywords,
                onDescriptionChange = { listIndex, descriptionIndex, newDesc ->
                    stepDescKeywords = stepDescKeywords.toMutableList().apply {
                        val targetStep = get(listIndex)
                        val update = targetStep.toMutableList().apply {
                            set(descriptionIndex, newDesc)
                        }
                        set(listIndex, update)
                    }
                },
                onAddRecipeDescription = { listIndex, descriptionIndex ->
                    recipeStepList = recipeStepList.toMutableList().apply {
                        val targetStep = get(listIndex)
                        val updatedDescriptions = targetStep.description.toMutableList().apply {
                            add(descriptionIndex + 1, "")
                        }
                        val updatedStep = targetStep.copy(description = updatedDescriptions)
                        set(listIndex, updatedStep)
                    }
                    stepDescKeywords = stepDescKeywords.toMutableList().apply {
                        val targetStep = get(listIndex)
                        val update = targetStep.toMutableList().apply {
                            add("")
                        }
                        set(listIndex, update)
                    }
                },
                onRemoveRecipeDescription = { listIndex, descriptionIndex ->
                    recipeStepList = recipeStepList.toMutableList().apply {
                        val targetStep = get(listIndex)
                        val currentDescriptions = targetStep.description

                        if (currentDescriptions.size <= 1) return@apply

                        val updatedDescriptions = currentDescriptions.toMutableList().apply {
                            removeAt(descriptionIndex)
                        }
                        val updatedStep = targetStep.copy(description = updatedDescriptions)
                        set(listIndex, updatedStep)
                    }
                    stepDescKeywords = stepDescKeywords.toMutableList().apply {
                        val targetStep = get(listIndex)
                        val update = targetStep.toMutableList().apply {
                            removeAt(descriptionIndex)
                        }
                        set(listIndex, update)
                    }
                },
                onAddRecipeStep = {
                    recipeStepList = recipeStepList.toMutableList().apply {
                        add(
                            RecipeStep(
                                step = recipeStepList.size + 1,
                                title = "",
                                description = listOf("")
                            )
                        )
                    }
                    stepTitleKeywords = stepTitleKeywords.toMutableList().apply {
                        add("")
                    }
                    stepDescKeywords = stepDescKeywords.toMutableList().apply {
                        add(listOf(""))
                    }
                },
                onRemoveRecipeStep = {
                    recipeStepList = recipeStepList.toMutableList().apply {
                        removeAt(it)
                    }
                    stepTitleKeywords = stepTitleKeywords.toMutableList().apply {
                        removeAt(it)
                    }
                    stepDescKeywords = stepDescKeywords.toMutableList().apply {
                        removeAt(it)
                    }
                }
            )
            MyRecipeSaveButton(
                onSaveMyRecipe = {
                    if (cookTimeMinKeyword.isEmpty()) {
                        Toast.makeText(context, "조리 시간을 입력해주세요", Toast.LENGTH_SHORT).show()
                        coroutineScope.launch {
                            cookTimeMinFocusRequester.requestFocus()
                        }
                        return@MyRecipeSaveButton
                    }
                    for (keyword in stepTitleKeywords) {
                        if (keyword.isEmpty()) {
                            Toast.makeText(context, "조리 방법을 작성해주세요.", Toast.LENGTH_SHORT).show()
                            return@MyRecipeSaveButton
                        }
                    }
                    for (step in stepDescKeywords) {
                        for (description in step) {
                            if (description.isEmpty()) {
                                Toast.makeText(context, "조리 상세 방법을 작성해주세요.", Toast.LENGTH_SHORT).show()
                                return@MyRecipeSaveButton
                            }
                        }
                    }
                    val recipeSteps = mutableListOf<RecipeStep>()
                    for (index in recipeStepList.indices) {
                        val recipeStep = RecipeStep(
                            step = index + 1,
                            title = stepTitleKeywords[index],
                            description = stepDescKeywords[index]
                        )
                        recipeSteps.add(recipeStep)
                    }
                    val recipe = Recipe(
                        type = RecipeType.MY_OWN,
                        menuName = menuName,
                        cookingTime = "${cookTimeHourKeyword}시간 ${cookTimeMinKeyword}분",
                        recipeSteps = recipeSteps
                    )
                    viewModel.saveMyRecipe(recipe)
                }
            )
        }
    }
}
