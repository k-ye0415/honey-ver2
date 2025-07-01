package com.jin.honey.feature.home.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.domain.model.food.MenuPreview
import com.jin.ui.theme.RecommendMenuBorderColor


@Composable
fun HomeRecommendMenu(menuPreviews: List<MenuPreview>, onNavigateToIngredient: (menuName: String) -> Unit) {
    Column(
        modifier = Modifier.padding(
            start = 10.dp,
            top = 10.dp, bottom = 10.dp
        )
    ) {
        Text(text = stringResource(R.string.food_search_recommend_menu), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(menuPreviews.size) {
                val item = menuPreviews[it]
                Box(
                    modifier = Modifier
                        .height(125.dp)
                        .clickable { onNavigateToIngredient(item.menuName) }
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(16.dp),
                                spotColor = Color.Black.copy(alpha = 0.3f)
                            )
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            AsyncImage(
                                model = item.menuImageUrl,
                                contentDescription = stringResource(R.string.home_recipe_menu_img_desc),
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, RecommendMenuBorderColor, CircleShape)
                                    .background(Color.White),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = item.menuName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .border(1.dp, RecommendMenuBorderColor, RoundedCornerShape(16.dp))
                            .padding(horizontal = 10.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.img_chat_honey_bee), // FIXME
                                contentDescription = stringResource(R.string.home_recipe_menu_category_icon_desc),
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(12.dp),
                                tint = Color.Unspecified
                            )
                            Text(text = item.type.categoryName, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
