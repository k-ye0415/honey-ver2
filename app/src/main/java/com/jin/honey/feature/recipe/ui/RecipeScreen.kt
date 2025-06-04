package com.jin.honey.feature.recipe.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.recipe.model.RecipePreview
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.AddRecipeBackgroundColor
import com.jin.honey.ui.theme.AddRecipeBorderColor
import com.jin.honey.ui.theme.AddRecipeRippleColor
import com.jin.honey.ui.theme.PointColor

@Composable
fun RecipeScreen(viewModel: RecipeViewModel, menuName: String) {
    val recipeState by viewModel.recipe.collectAsState()

    LaunchedEffect(menuName) {
        viewModel.findRecipeByMenuName(menuName)
    }

    when (val state = recipeState) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> RecipeSuccessScreen(state.data)
        is UiState.Error -> Text("음 실패?")
    }
}

@Composable
private fun RecipeSuccessScreen(recipe: RecipePreview) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // title
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton({}) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = stringResource(R.string.ingredient_back_icon_desc),
                        tint = Color.Black
                    )
                }
                Text(
                    text = stringResource(R.string.recipe_title),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .height(80.dp)
            ) {
                AsyncImage(
                    model = recipe.menuImageUrl,
                    contentDescription = stringResource(R.string.recipe_menu_img_desc),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(recipe.menuName, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    Text(recipe.recipe.cookingTime, fontSize = 14.sp)
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                        SubButtonBox(
                            modifier = Modifier,
                            btnText = stringResource(R.string.recipe_ask_chat_gpt),
                            backgroundColor = Color.White,
                            rippleColor = PointColor,
                            textColor = Color.Black
                        ) { }
                    }
                }
            }
            HorizontalDivider()
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp), contentPadding = PaddingValues(10.dp)) {
                items(recipe.recipe.recipeSteps.size) {
                    val recipeStep = recipe.recipe.recipeSteps[it]
                    Column {
                        Text(recipeStep.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
                        for ((index, step) in recipeStep.description.withIndex()) {
                            Text("${index + 1}. $step", modifier = Modifier.padding(start = 8.dp))
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
            MyRecipe()
        }
    }
}

@Composable
private fun SubButtonBox(
    modifier: Modifier,
    btnText: String,
    backgroundColor: Color,
    rippleColor: Color,
    textColor: Color,
    onClickButton: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(backgroundColor)
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = rippleColor,
                    bounded = true,
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClickButton
            )
            .border(1.dp, PointColor, RoundedCornerShape(30.dp))
            .padding(start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(btnText, fontSize = 12.sp, color = textColor, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun MyRecipe() {
    var isExpanded by remember { mutableStateOf(true) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.recipe_my_recipe),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = stringResource(R.string.ingredient_show_ingredient_icon_desc)
            )
        }
        HorizontalDivider()
        AnimatedVisibility(isExpanded) {
            val interactionSource = remember { MutableInteractionSource() }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.recipe_my_recipe_no_exist),
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(AddRecipeBackgroundColor)
                        .indication(interactionSource, rememberRipple(color = AddRecipeRippleColor, bounded = true))
                        .clickable(interactionSource = interactionSource, indication = null, onClick = {})
                        .border(1.dp, AddRecipeBorderColor, RoundedCornerShape(8.dp))
                        .padding(PaddingValues(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.ingredient_add_my_recipe),
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
