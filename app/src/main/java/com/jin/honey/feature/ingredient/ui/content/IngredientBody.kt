package com.jin.honey.feature.ingredient.ui.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.Ingredient

@Composable
fun IngredientBody(
    menuName: String,
    ingredientList: List<Ingredient>,
    isAllIngredientChecked: Boolean,
    onAllCheckedChange: (newCheck: Boolean) -> Unit,
    ingredientCheckMap: Map<String, Boolean>,
    onCheckChanged: (menuName: String, newCheck: Boolean) -> Unit
) {
    val totalPrice = ingredientList.sumOf { it.unitPrice }
    Text(
        text = stringResource(R.string.ingredient_all_add_cart),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    IngredientItem(
        ingredient = Ingredient(menuName, "", totalPrice),
        isChecked = isAllIngredientChecked,
        onCheckChanged = onAllCheckedChange
    )
    HorizontalDivider(color = Color.LightGray)
    IngredientAccordion(
        ingredientList = ingredientList,
        checkState = ingredientCheckMap,
        onCheckChanged = onCheckChanged,
    )
}

@Composable
fun IngredientAccordion(
    ingredientList: List<Ingredient>,
    checkState: Map<String, Boolean>,
    onCheckChanged: (menuName: String, newCheck: Boolean) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(true) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.ingredient_show_ingredient),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = stringResource(R.string.ingredient_show_ingredient_icon_desc)
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            Column {
                for (ingredient in ingredientList) {
                    val isChecked = checkState[ingredient.name] ?: false
                    IngredientItem(
                        ingredient,
                        isChecked = isChecked,
                        onCheckChanged = { newCheck -> onCheckChanged(ingredient.name, newCheck) })
                }
            }
        }
    }
}

@Composable
private fun IngredientItem(ingredient: Ingredient, isChecked: Boolean, onCheckChanged: (newCheck: Boolean) -> Unit) {
    Column {
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = isChecked, onCheckedChange = onCheckChanged)
            Text(ingredient.name)
            Spacer(Modifier.width(4.dp))
            Text(ingredient.quantity)
            Spacer(Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = { /* 수량 증가 */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.ingredient_plus_quantity_icon_desc),
                        modifier = Modifier.size(12.dp)
                    )
                }
                Text(
                    "1",
                    modifier = Modifier.width(20.dp),
                    textAlign = TextAlign.Center
                )
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = { /* 수량 감소 */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = stringResource(R.string.ingredient_remove_quantity_icon_desc),
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
            Text(
                "${ingredient.unitPrice}원",
                modifier = Modifier
                    .width(80.dp)
                    .padding(end = 20.dp),
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider()
    }
}
