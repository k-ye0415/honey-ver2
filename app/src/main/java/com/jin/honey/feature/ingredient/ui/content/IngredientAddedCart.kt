package com.jin.honey.feature.ingredient.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jin.honey.feature.cart.IngredientCart
import com.jin.honey.ui.theme.PointColor

@Composable
fun IngredientAddedCart(
    modifier: Modifier,
    ingredientCartMap: Map<String, IngredientCart>,
    onAddedCart: () -> Unit
) {
    val ingredients = ingredientCartMap.keys.map { it }
    val menuName = ingredientCartMap.values.firstOrNull()?.menuName.orEmpty()
    val selectedIngredient = if (ingredients.size > 1) {
        "${ingredients.firstOrNull()} 외 ${ingredients.size - 1}"
    } else {
        "${ingredients.firstOrNull()}"
    }
    val selectedDescription = "${menuName}의 재료 $selectedIngredient 선택되었습니다."
    val totalPrice = ingredientCartMap.values.sumOf { it.totalPrice }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .border(1.dp, PointColor, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(selectedDescription, modifier = Modifier.padding(top = 14.dp))
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                onClick = onAddedCart
            ) {
                Text("${totalPrice}원 배달 주문하기")
            }
        }
    }
}
