package com.jin.ui.home.content

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.ui.R
import com.jin.domain.recipe.model.RecipePreview
import com.jin.ui.theme.PointColor

@Composable
fun HomeRecommendRecipe(recommendRecipes: List<RecipePreview>, onNavigateToRecipe: (menuName: String) -> Unit) {
    Column(
        modifier = Modifier.padding(
            start = 10.dp,
            top = 10.dp, bottom = 10.dp
        )
    ) {
        Text(text = stringResource(R.string.home_recipe), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(recommendRecipes.size) {
                val item = recommendRecipes[it]
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, PointColor, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .clickable { onNavigateToRecipe(item.menuName) }
                ) {
                    Row {
                        AsyncImage(
                            model = item.menuImageUrl,
                            contentDescription = stringResource(R.string.home_recipe_menu_img_desc),
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .height(60.dp)
                                .padding(start = 8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.img_chat_honey_bee), // FIXME
                                    contentDescription = stringResource(R.string.home_recipe_menu_category_icon_desc),
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .size(14.dp),
                                    tint = Color.Unspecified
                                )
                                Text(text = item.categoryType.categoryName, fontSize = 14.sp)
                            }
                            Text(text = item.menuName, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
