package com.jin.honey.feature.order.ui.content.cart.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.CartKey
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.ui.theme.PointColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartOptionModifyBottomSheet(
    cartItems: List<Cart>,
    onRemoveCart: (cartItem: Cart, ingredient: IngredientCart) -> Unit,
    onBottomSheetClose: (state: Boolean) -> Unit,
    onChangeOption: (quantityMap: Map<CartKey, Int>) -> Unit,
) {
    val quantityMap = remember {
        mutableMapOf<CartKey, Int>().apply {
            for (cart in cartItems) {
                for (ingredient in cart.ingredients) {
                    put(CartKey(cart.menuName, ingredient.name), ingredient.cartQuantity)
                }
            }
        }
    }
    ModalBottomSheet(
        onDismissRequest = { onBottomSheetClose(false) },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null
    ) {
        Box(modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                BottomSheetHeader(onBottomSheetClose)
                LazyColumn(contentPadding = PaddingValues(bottom = 4.dp)) {
                    items(cartItems.size) {
                        val cartItem = cartItems[it]
                        IngredientItems(
                            cartItem.menuName,
                            cartItem.ingredients,
                            quantityMap,
                            onQuantityChange = { ingredientName, newQuantity ->
                                quantityMap[CartKey(
                                    cartItem.menuName,
                                    ingredientName
                                )] = newQuantity
                            },
                            onRemoveCart = { ingredient -> onRemoveCart(cartItem, ingredient) }
                        )
                    }
                }
            }
            Row(modifier = Modifier.align(Alignment.BottomCenter)) {
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
                    onClick = { onChangeOption(quantityMap) }
                ) {
                    Text(stringResource(R.string.cart_modify_option_modify))
                }
            }
        }
    }
}

@Composable
private fun BottomSheetHeader(onBottomSheetClose: (state: Boolean) -> Unit) {
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
            onClick = { onBottomSheetClose(false) }
        ) {
            Icon(
                modifier = Modifier.scale(0.7f),
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.cart_modify_option_close_icon_desc)
            )
        }
    }
}

@Composable
private fun IngredientItems(
    menuName: String,
    ingredients: List<IngredientCart>,
    quantityMap: Map<CartKey, Int>,
    onQuantityChange: (ingredientName: String, newQuantity: Int) -> Unit,
    onRemoveCart: (ingredient: IngredientCart) -> Unit,
) {
    Column {
        Text(
            menuName,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 4.dp)
        )
        HorizontalDivider()
        for (ingredient in ingredients) {
            var cartQuantity by remember {
                val quantity = quantityMap[CartKey(menuName, ingredient.name)] ?: 1
                mutableIntStateOf(quantity)
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${ingredient.name} ${ingredient.quantity}")
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        modifier = Modifier.size(32.dp),
                        onClick = {
                            if (cartQuantity < 10) {
                                cartQuantity++
                                onQuantityChange(ingredient.name, cartQuantity)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.ingredient_plus_quantity_icon_desc),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(
                        text = "$cartQuantity",
                        modifier = Modifier.width(20.dp),
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        modifier = Modifier.size(32.dp),
                        onClick = {
                            if (cartQuantity > 1) {
                                cartQuantity--
                                onQuantityChange(ingredient.name, cartQuantity)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = stringResource(R.string.ingredient_remove_quantity_icon_desc),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    IconButton(
                        modifier = Modifier.size(32.dp),
                        onClick = { onRemoveCart(ingredient) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}
