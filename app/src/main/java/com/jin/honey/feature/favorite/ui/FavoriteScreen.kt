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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.favorite.domain.FavoritePreview
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.FavoriteCountTextColor
import com.jin.honey.ui.theme.FavoriteTitleBackgroundColor
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.ReviewStarColor

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel, onNavigateToIngredient: (menuName: String) -> Unit) {
    val favoriteMenuState by viewModel.favoriteMenuState.collectAsState()
    val recentlyMenuState by viewModel.recentlyMenuState.collectAsState()

    val favoriteMenus = when (val state = favoriteMenuState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val recentlyMenus = when (val state = recentlyMenuState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

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
                        .background(FavoriteTitleBackgroundColor)
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
                    Text(
                        text = stringResource(R.string.favorite_count, favoriteMenus.size),
                        color = FavoriteCountTextColor
                    )
                }
            }
            item {
                FavoriteList(
                    favoriteMenus = favoriteMenus,
                    onNavigateToIngredient = onNavigateToIngredient,
                    onToggleFavorite = { viewModel.toggleFavoriteMenu(menuName = it) })
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(FavoriteTitleBackgroundColor)
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
                    Text(
                        text = stringResource(R.string.favorite_count, recentlyMenus.size),
                        color = FavoriteCountTextColor
                    )
                }
            }
            item {
                RecentlyMenu(
                    recentlyMenus = recentlyMenus,
                    onNavigateToIngredient = onNavigateToIngredient,
                    onDeleteRecentlyMenu = { viewModel.deleteRecentlyMenu(menuName = it) },
                    onToggleFavorite = { viewModel.toggleFavoriteMenu(menuName = it) }
                )
            }
        }
    }
}

@Composable
fun FavoriteList(
    favoriteMenus: List<FavoritePreview>,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onToggleFavorite: (menuName: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        for (item in favoriteMenus) {
            Row(modifier = Modifier.clickable { onNavigateToIngredient(item.menuName) }) {
                AsyncImage(
                    model = item.imageUrl,
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
                    Text(item.menuName)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                            tint = ReviewStarColor,
                        )
                        Text(String.format("%.1f", item.reviewScore), fontSize = 14.sp)
                        Text("(${item.reviewCount})", fontSize = 14.sp, color = FavoriteCountTextColor)
                    }
                }
                IconButton(modifier = Modifier.height(80.dp), onClick = { onToggleFavorite(item.menuName) }) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = stringResource(R.string.favorite_menu_favorite_icon_desc),
                        tint = PointColor
                    )
                }
            }
            if (item != favoriteMenus.last()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
            }
        }
    }
}

@Composable
fun RecentlyMenu(
    recentlyMenus: List<FavoritePreview>,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onDeleteRecentlyMenu: (menuName: String) -> Unit,
    onToggleFavorite: (menuName: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        for (item in recentlyMenus) {
            Row(modifier = Modifier.clickable { onNavigateToIngredient(item.menuName) }) {
                AsyncImage(
                    model = item.imageUrl,
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
                    Text(item.menuName)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                            tint = ReviewStarColor,
                        )
                        Text(String.format("%.1f", item.reviewScore), fontSize = 14.sp)
                        Text("(${item.reviewCount})", fontSize = 14.sp, color = FavoriteCountTextColor)
                    }
                }
                Column(
                    modifier = Modifier.height(80.dp),
                ) {
                    Box(modifier = Modifier.clickable { onDeleteRecentlyMenu(item.menuName) }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.favorite_recently_menu_clear_icon_desc),
                            tint = FavoriteCountTextColor
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Box(modifier = Modifier.clickable { onToggleFavorite(item.menuName) }) {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorite_recently_menu_favorite_icon_desc)
                        )
                    }
                }
            }
            if (item != recentlyMenus.last()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))
            }
        }
    }
}
