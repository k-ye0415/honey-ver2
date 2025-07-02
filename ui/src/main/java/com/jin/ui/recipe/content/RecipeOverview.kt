package com.jin.ui.recipe.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.ui.R
import com.jin.ui.theme.PointColor

@Composable
fun RecipeOverview(imageUrl: String, menuName: String, cookingTime: String, onNavigateToChatBot: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .height(80.dp)
    ) {
        AsyncImage(
            model = imageUrl,
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
            Text(menuName, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Text(cookingTime, fontSize = 14.sp)
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                SubButtonBox(
                    modifier = Modifier,
                    btnText = stringResource(R.string.recipe_ask_chat_gpt),
                    backgroundColor = Color.White,
                    rippleColor = PointColor,
                    textColor = Color.Black,
                    onClickButton = { onNavigateToChatBot() }
                )
            }
        }
    }
    HorizontalDivider()
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
