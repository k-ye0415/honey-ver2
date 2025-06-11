package com.jin.honey.feature.home.ui.content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jin.honey.R
import kotlinx.coroutines.delay

@Composable
fun HomeBanner() {
    val bannerImages = remember {
        listOf(
            R.drawable.img_home_banner_1,
            R.drawable.img_home_banner_2,
            R.drawable.img_home_banner_3,
            R.drawable.img_home_banner_4
        )
    }
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000L)
            val newIndex = (currentIndex + 1) % bannerImages.size
            currentIndex = newIndex
        }
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        AnimatedContent(
            targetState = bannerImages[currentIndex],
            transitionSpec = {
                val enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                ) + fadeIn(animationSpec = tween(durationMillis = 300))

                val exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                ) + fadeOut(animationSpec = tween(durationMillis = 300))

                enter togetherWith exit using SizeTransform(clip = false)
            }
        ) { image ->
            AsyncImage(
                model = image,
                contentDescription = stringResource(R.string.home_banner_img_desc),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}
