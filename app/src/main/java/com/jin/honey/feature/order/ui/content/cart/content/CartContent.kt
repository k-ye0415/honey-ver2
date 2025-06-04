package com.jin.honey.feature.order.ui.content.cart.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.ui.theme.PointColor

@Composable
fun CartContent(cartItems: List<IngredientCart>, onBottomSheetClose: (state: Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(modifier = Modifier.height(140.dp)) {
            AsyncImage(
                model = cartItems.firstOrNull()?.menuImageUrl.orEmpty(),
                contentDescription = stringResource(R.string.cart_menu_img_desc),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(140.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                val menuNames = if (cartItems.size > 1) {
                    "${cartItems.firstOrNull()?.menuName.orEmpty()} 외 ${cartItems.size - 1}"
                } else {
                    cartItems.firstOrNull()?.menuName.orEmpty()
                }
                val ingredientNames = StringBuilder()
                for (item in cartItems) {
                    val ingredients = item.ingredients
                    ingredientNames.append("[")
                    for (ingredient in ingredients) {
                        if (ingredient == ingredients.last()) {
                            ingredientNames.append("${ingredient.name} ${ingredient.quantity}")
                        } else {
                            ingredientNames.append("${ingredient.name} ${ingredient.quantity}, ")
                        }
                    }
                    if (item == cartItems.last()) {
                        ingredientNames.append("]")
                    } else {
                        ingredientNames.append("], ")
                    }
                }
                Text(menuNames, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(ingredientNames.toString(), fontSize = 14.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.weight(1f))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                    SubButtonBox(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 2.dp),
                        btnText = stringResource(R.string.cart_modify_option),
                        backgroundColor = Color.White,
                        rippleColor = PointColor,
                        textColor = Color.Black,
                        onClickButton = { onBottomSheetClose(true) }
                    )
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
