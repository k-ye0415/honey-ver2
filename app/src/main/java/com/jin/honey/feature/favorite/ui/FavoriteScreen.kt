package com.jin.honey.feature.favorite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.jin.honey.ui.theme.ReviewStarColor

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        ForPreview(Modifier.padding(innerPadding))
    }
}

@Composable
fun ForPreview(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "찜",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Row {
            Text("찜한 메뉴")
            Text("${fallbackFavoriteData.size}개")
        }
        LazyColumn {
            items(fallbackFavoriteData.size) {
                val item = fallbackFavoriteData[it]
                Row {
                    AsyncImage(
                        model = "",
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(item)
                        Row {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                tint = ReviewStarColor,
                            )
                            Text("5.0")
                        }
                    }
                    Icon(Icons.Default.Favorite, contentDescription = null)
                }
            }
        }

        Row {
            Text("최근 본 메뉴")
            Text("${fallbackRecentShowData.size}개")
        }
        LazyColumn {
            items(fallbackRecentShowData.size) {
                val item = fallbackRecentShowData[it]
                Row {
                    AsyncImage(
                        model = "",
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(item)
                        Row {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                                tint = ReviewStarColor,
                            )
                            Text("5.0")
                        }
                    }
                    Column {
                        Icon(Icons.Default.Close, contentDescription = null)
                        Icon(Icons.Default.Favorite, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ForPreview21() {
    HoneyTheme {
        ForPreview(Modifier.fillMaxSize())
    }
}

val fallbackFavoriteData = listOf("찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴")
val fallbackRecentShowData = listOf("찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴", "찜 메뉴")
