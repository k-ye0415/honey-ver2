package com.jin.honey.feature.order.ui

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.food.domain.model.Recipe
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PointColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(viewModel: OrderViewModel) {
    var showBottomSheet by remember { mutableStateOf(false) }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            dragHandle = null
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.cart_modify_option),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        modifier = Modifier.size(32.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                        ),
                        onClick = { showBottomSheet = false }
                    ) {
                        Icon(
                            modifier = Modifier.scale(0.7f),
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.cart_modify_option_close_icon_desc)
                        )
                    }
                }
                LazyColumn(contentPadding = PaddingValues(bottom = 4.dp)) {
                    items(cartFallback.size) {
                        val menu = cartFallback[it]
                        Column {
                            Text(
                                menu.name,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            HorizontalDivider()
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .padding(start = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val ingredientList = menu.ingredient
                                for (ingredient in ingredientList) {
                                    Text(ingredient.name)
                                    Spacer(Modifier.weight(1f))
                                    Row {
                                        IconButton(
                                            modifier = Modifier.size(32.dp),
                                            onClick = {}
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = stringResource(R.string.ingredient_plus_quantity_icon_desc),
                                                modifier = Modifier.size(12.dp)
                                            )
                                        }
                                        Text(
                                            "${ingredient.quantity}",
                                            modifier = Modifier.width(20.dp),
                                            textAlign = TextAlign.Center
                                        )
                                        IconButton(
                                            modifier = Modifier.size(32.dp),
                                            onClick = {}
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Remove,
                                                contentDescription = stringResource(R.string.ingredient_remove_quantity_icon_desc),
                                                modifier = Modifier.size(12.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Row {
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                        onClick = {}
                    ) {
                        Text(stringResource(R.string.cart_modify_option_cancel))
                    }
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                        onClick = {}
                    ) {
                        Text(stringResource(R.string.cart_modify_option_modify))
                    }
                }
            }
        }
    }
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
        // cart
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.ShoppingCart, contentDescription = stringResource(R.string.order_cart_icon_desc))
            Text(stringResource(R.string.order_cart_title), fontSize = 18.sp)
        }
        HorizontalDivider()
        // cart content
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(modifier = Modifier.height(100.dp)) {
                AsyncImage(
                    model = "",
                    contentDescription = stringResource(R.string.cart_menu_img_desc),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("메뉴 이름 외 1", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text("재료들이 쭈루룩~", fontSize = 14.sp)
                    Spacer(Modifier.weight(1f))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                        SubButtonBox(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 2.dp),
                            btnText = stringResource(R.string.cart_modify_option),
                            backgroundColor = Color.White,
                            rippleColor = PointColor,
                            textColor = Color.Black
                        ) {
                            showBottomSheet = true
                        }
                        SubButtonBox(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 2.dp),
                            btnText = stringResource(R.string.cart_order),
                            backgroundColor = PointColor,
                            rippleColor = PointColor,
                            textColor = Color.White
                        ) {}
                    }
                }
            }
        }

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
