package com.jin.ui.recipe.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.RoundedBoxButton
import com.jin.domain.recipe.model.Recipe
import com.jin.domain.recipe.model.RecipeStep
import com.jin.domain.recipe.model.RecipeType
import com.jin.ui.R
import com.jin.ui.theme.AddRecipeBackgroundColor
import com.jin.ui.theme.AddRecipeBorderColor
import com.jin.ui.theme.AddRecipeRippleColor
import com.jin.ui.theme.HoneyTheme

@Composable
fun MyRecipe(onNavigateToMyRecipe: () -> Unit) {
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
            if (recipeFallback != null) {
                RoundedBoxButton(
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = AddRecipeBackgroundColor,
                    borderColor = AddRecipeBorderColor,
                    rippleColor = AddRecipeRippleColor,
                    contentPadding = PaddingValues(8.dp),
                    onClick = {}
                ) {
                    Row {
                        Text(recipeFallback.menuName, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.weight(1f))
                        Text(recipeFallback.cookingTime)
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.recipe_my_recipe_no_exist),
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    RoundedBoxButton(
                        modifier = Modifier,
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = AddRecipeBackgroundColor,
                        borderColor = AddRecipeBorderColor,
                        rippleColor = AddRecipeRippleColor,
                        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                        onClick = onNavigateToMyRecipe
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
}

@Preview(showBackground = true)
@Composable
fun preview123() {
    HoneyTheme {
        MyRecipe { }
    }
}

val recipeFallback = Recipe(
    type = RecipeType.MY_OWN,
    menuName = "Jarvis Richard",
    cookingTime = "bibendum",
    recipeSteps = listOf(
        RecipeStep(
            step = 8793, title = "viris", description = listOf(
                "backgroundColor = AddRecipeBackgroundColor",
                "borderColor = AddRecipeBorderColor",
                "rippleColor = AddRecipeRippleColor,"
            )
        )
    )
)
