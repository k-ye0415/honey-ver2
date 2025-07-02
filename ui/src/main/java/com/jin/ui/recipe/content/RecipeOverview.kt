package com.jin.ui.recipe.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.jin.RoundedBoxButton
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
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                RoundedBoxButton(
                    modifier = Modifier,
                    shape = RoundedCornerShape(30.dp),
                    backgroundColor = Color.White,
                    borderColor = PointColor,
                    rippleColor = PointColor,
                    contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
                    onClick = onNavigateToChatBot
                ) {
                    Text(
                        stringResource(R.string.recipe_ask_chat_gpt),
                        fontSize = 12.sp,
                        color = PointColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
    HorizontalDivider()
}
