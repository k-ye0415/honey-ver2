package com.jin.honey.feature.ingredient.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jin.honey.R

@Composable
fun IngredientHeader(
    imageUrl: String,
    statusTopHeightDp: Dp,
    onNavigateToCategory: () -> Unit,
    onClickShare: () -> Unit,
    onClickFavorite: () -> Unit,
) {
    Box {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.menu_image_desc),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .padding(top = statusTopHeightDp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp)
        ) {
            HeaderIconButton(
                Icons.Default.ArrowBackIosNew,
                stringResource(R.string.ingredient_back_icon_desc),
                onNavigateToCategory
            )
            Spacer(Modifier.weight(1f))
            // FIXME : Share 기능 필요
            HeaderIconButton(
                Icons.Default.Share,
                stringResource(R.string.ingredient_share_icon_desc),
                onClickShare
            )
            Spacer(Modifier.width(8.dp))
            HeaderIconButton(
                Icons.Default.FavoriteBorder,
                stringResource(R.string.menu_favorite_icon_desc),
                onClickFavorite
            )
        }
    }
}

@Composable
private fun HeaderIconButton(icon: ImageVector, iconDes: String, onClickEvent: (() -> Unit)?) {
    IconButton(
        modifier = Modifier.size(32.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
        ),
        onClick = { onClickEvent?.invoke() }
    ) {
        Icon(
            modifier = Modifier.scale(0.7f),
            imageVector = icon,
            contentDescription = iconDes
        )
    }
}
