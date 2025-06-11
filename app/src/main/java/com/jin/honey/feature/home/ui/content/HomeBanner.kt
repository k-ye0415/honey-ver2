package com.jin.honey.feature.home.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jin.honey.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val pagerState = rememberPagerState { bannerImages.size }
    LaunchedEffect(pagerState.currentPage) {
        launch {
            while (true) {
                delay(5000L)
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = bannerImages[page],
                contentDescription = stringResource(R.string.home_banner_img_desc),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}
