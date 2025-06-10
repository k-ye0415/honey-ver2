package com.jin.honey.feature.favorite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.ReviewStarColor

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel) {
    ForPreview()
}

@Composable
fun ForPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.favorite_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(
                            top = 24.dp, bottom = 14.dp,
                            start = 20.dp, end = 20.dp
                        ),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(R.string.favorite_menu),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 8.dp),
                        fontSize = 18.sp
                    )
                    Text(text = stringResource(R.string.favorite_count, fallbackFavoriteData.size))
                }
            }
            item {
                FavoriteList()
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(
                            top = 24.dp, bottom = 14.dp,
                            start = 20.dp, end = 20.dp
                        ),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(R.string.favorite_recently_menu),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 8.dp),
                        fontSize = 18.sp
                    )
                    Text(text = stringResource(R.string.favorite_count, fallbackRecentShowData.size))
                }
            }
            item {
                RecentlyMenu()
            }
        }
    }
}

@Composable
fun FavoriteList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        for (item in fallbackFavoriteData) {
            Row {
                AsyncImage(
                    model = "",
                    contentDescription = stringResource(R.string.favorite_menu_img_desc),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(item)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                            tint = ReviewStarColor,
                        )
                        Text("5.0", fontSize = 14.sp)
                        Text("(3)", fontSize = 14.sp)
                    }
                }
                IconButton(modifier = Modifier.height(80.dp), onClick = {}) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = stringResource(R.string.favorite_menu_favorite_icon_desc),
                        tint = PointColor
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
        }
    }
}

@Composable
fun RecentlyMenu() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        for (item in fallbackRecentShowData) {
            Row {
                AsyncImage(
                    model = "",
                    contentDescription = stringResource(R.string.favorite_recently_menu_img_desc),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(item)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                            tint = ReviewStarColor,
                        )
                        Text("5.0", fontSize = 14.sp)
                        Text("(3)", fontSize = 14.sp)
                    }
                }
                Column(
                    modifier = Modifier.height(80.dp),
                ) {
                    Box(modifier = Modifier.clickable { }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.favorite_recently_menu_clear_icon_desc)
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Box(modifier = Modifier.clickable { }) {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorite_recently_menu_favorite_icon_desc)
                        )
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ForPreview21() {
    HoneyTheme {
        ForPreview()
    }
}

val fallbackFavoriteData = listOf("찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴")
val fallbackRecentShowData = listOf("찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴")
