package com.jin.honey.feature.order.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.food.domain.model.Recipe
import com.jin.honey.feature.order.ui.content.cart.CartScreen
import com.jin.honey.ui.theme.HoneyTheme

@Composable
fun OrderScreen(viewModel: OrderViewModel) {
    Column() {
        // title
        Text(
            text = stringResource(R.string.order_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        CartScreen()
        // order
        Text(stringResource(R.string.order_history_title))
        HorizontalDivider()
        // FIXME : 주문내역 정의 후 UI 수정
        LazyColumn {
            items(orderFallback.size) {
                Text(orderFallback[it])
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun OrderScreenPreview() {
    HoneyTheme {
        OrderScreen(OrderViewModel())
    }
}

val orderFallback = listOf("주문내역", "주문내역", "주문내역", "주문내역", "주문내역", "주문내역")
val cartFallback = listOf(
    Menu(
        name = "치즈버거",
        imageUrl = "",
        recipe = Recipe(cookingTime = "", recipeSteps = listOf()),
        ingredient = listOf(Ingredient(name = "햄버거번", quantity = "1", unitPrice = 0))
    ),
    Menu(
        name = "치즈버거",
        imageUrl = "",
        recipe = Recipe(cookingTime = "", recipeSteps = listOf()),
        ingredient = listOf(Ingredient(name = "햄버거번", quantity = "1", unitPrice = 0))
    ),
    Menu(
        name = "치즈버거",
        imageUrl = "",
        recipe = Recipe(cookingTime = "", recipeSteps = listOf()),
        ingredient = listOf(Ingredient(name = "햄버거번", quantity = "1", unitPrice = 0))
    )
)
