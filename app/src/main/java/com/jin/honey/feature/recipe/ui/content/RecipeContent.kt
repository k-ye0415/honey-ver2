package com.jin.honey.feature.recipe.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jin.honey.feature.food.domain.model.RecipeStep

@Composable
fun RecipeContent(recipeSteps: List<RecipeStep>) {
    for (recipeStep in recipeSteps) {
        Column(modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)) {
            Text(
                text = recipeStep.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            for ((index, step) in recipeStep.description.withIndex()) {
                Text("${index + 1}. $step", modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}
