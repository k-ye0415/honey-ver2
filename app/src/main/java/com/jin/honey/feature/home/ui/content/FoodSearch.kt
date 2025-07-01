package com.jin.honey.feature.home.ui.content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jin.honey.R
import com.jin.model.food.MenuPreview
import com.jin.ui.theme.FoodSearchBoxBorderColor
import com.jin.ui.theme.FoodSearchBoxTextColor
import kotlinx.coroutines.delay

@Composable
fun FoodSearch(
    recommendMenus: List<MenuPreview>,
    onNavigateToFoodSearch: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000L)
            currentIndex = (currentIndex + 1) % recommendMenus.size
        }
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable(onClick = onNavigateToFoodSearch)
            .border(1.dp, FoodSearchBoxBorderColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = recommendMenus[currentIndex],
                transitionSpec = {
                    (slideInVertically { height -> height } + fadeIn())
                        .togetherWith(slideOutVertically { height -> -height } + fadeOut())
                },
                label = "추천 메뉴"
            ) { menu ->
                Row {
                    Text(
                        text = "${currentIndex + 1}",
                        color = FoodSearchBoxTextColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = menu.menuName, color = FoodSearchBoxTextColor)
                }
            }
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.district_search_icon_desc)
            )
        }
    }
}
