package com.jin.honey.feature.reviewwrite.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.food.domain.model.Recipe
import com.jin.honey.feature.reviewwrite.ui.content.MenuReviewWriteScreen
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.HoneyTheme
import kotlinx.coroutines.launch

@Composable
fun ReviewWriteScreen(viewModel: ReviewWriteViewModel, orderKey: String) {
    val orderDetailState by viewModel.orderDetailState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchOrderDetail(orderKey)
    }

    val orderDetail = when (val state = orderDetailState) {
        is UiState.Success -> state.data
        else -> null
    }
    val orderMenuList = orderDetail?.cart ?: emptyList()

    val pagerState = rememberPagerState(initialPage = 0) { orderMenuList.size }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton({}) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.review_back_icon_desc),
                        tint = Color.Black
                    )
                }
                Text(
                    text = stringResource(R.string.review_write_title),
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = stringResource(R.string.review_write_description),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 14.dp),
                fontWeight = FontWeight.Bold
            )
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
            ) { page ->
                val isLastPage = page == orderMenuList.lastIndex
                val btnText = if (isLastPage)
                    stringResource(R.string.review_write_btn_text_save)
                else stringResource(R.string.review_write_btn_text_next)
                MenuReviewWriteScreen(
                    orderItems = orderMenuList[page],
                    btnText = btnText,
                    onNextClick = {
                        coroutineScope.launch {
                            if (!isLastPage) {
                                pagerState.animateScrollToPage(page + 1)
                            } else {
                                // TODO
                            }
                        }
                    }
                )
            }
        }
    }
}
