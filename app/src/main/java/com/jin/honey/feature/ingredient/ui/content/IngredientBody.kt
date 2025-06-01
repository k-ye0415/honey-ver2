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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.jin.honey.feature.cart.IngredientCart
import com.jin.honey.feature.food.domain.model.Ingredient

@Composable
fun IngredientBody(
    menuName: String,
    ingredientList: List<Ingredient>,
    allIngredientsSelected: Boolean,
    ingredientSelections: Map<String, IngredientCart>,
    onAllCheckedChange: (newCheck: Boolean, totalQuantity: Int, totalPrice: Int) -> Unit,
    onCheckChanged: (menuName: String, newCheck: Boolean, totalQuantity: Int, totalPrice: Int) -> Unit,
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
        isChecked = allIngredientsSelected,
        onCheckChanged = onAllCheckedChange,
    )
    HorizontalDivider(color = Color.LightGray)
    IngredientAccordion(
        ingredientList = ingredientList,
        isAllIngredientChecked = allIngredientsSelected,
        checkState = ingredientSelections,
        onCheckChanged = onCheckChanged,
    )
}

@Composable
fun IngredientAccordion(
    ingredientList: List<Ingredient>,
    isAllIngredientChecked: Boolean,
    checkState: Map<String, IngredientCart>,
    onCheckChanged: (menuName: String, newCheck: Boolean, totalQuantity: Int, totalPrice: Int) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(true) }

    LaunchedEffect(isAllIngredientChecked) {
        isExpanded = !isAllIngredientChecked
    }

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
                    val ingredientCart = checkState[ingredient.name]
                    IngredientItem(
                        ingredient,
                        isChecked = ingredientCart?.isSelected ?: false,
                        onCheckChanged = { newCheck, totalQuantity, totalPrice ->
                            onCheckChanged(
                                ingredient.name,
                                newCheck,
                                totalQuantity,
                                totalPrice
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun IngredientItem(
    ingredient: Ingredient,
    isChecked: Boolean,
    onCheckChanged: (newCheck: Boolean, totalQuantity: Int, totalPrice: Int) -> Unit,
) {
    var quantity by remember { mutableIntStateOf(1) }
    Column {
        Row(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clickable {
                    onCheckChanged(!isChecked, quantity, (quantity * ingredient.unitPrice))
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { onCheckChanged(!isChecked, quantity, ((quantity * ingredient.unitPrice))) })
            Text(ingredient.name)
            Spacer(Modifier.width(4.dp))
            Text(ingredient.quantity)
            Spacer(Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = {
                        quantity++
                        onCheckChanged(isChecked, quantity, ((quantity * ingredient.unitPrice)))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.ingredient_plus_quantity_icon_desc),
                        modifier = Modifier.size(12.dp)
                    )
                }
                Text(
                    "$quantity",
                    modifier = Modifier.width(20.dp),
                    textAlign = TextAlign.Center
                )
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = {
                        if (quantity > 1) quantity--
                        onCheckChanged(isChecked, quantity, ((quantity * ingredient.unitPrice)))
                    }
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
