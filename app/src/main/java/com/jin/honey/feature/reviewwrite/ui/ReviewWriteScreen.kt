package com.jin.honey.feature.reviewwrite.ui

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.feature.ui.state.DbState
import com.jin.feature.ui.state.UiState
import com.jin.honey.R
import com.jin.honey.feature.reviewwrite.ui.content.MenuReviewWriteScreen
import com.jin.model.food.CategoryType
import com.jin.model.review.Review
import com.jin.model.review.ReviewContent
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun ReviewWriteScreen(viewModel: ReviewWriteViewModel, orderKey: String, onNavigateToOrder: () -> Unit) {
    val context = LocalContext.current
    val orderDetailState by viewModel.orderDetailState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchOrderDetail(orderKey)
    }

    LaunchedEffect(Unit) {
        viewModel.insertState.collect {
            when (it) {
                is DbState.Success -> {
                    Toast.makeText(
                        context,
                        "리뷰 작성 완료",
                        Toast.LENGTH_SHORT
                    ).show()
                    onNavigateToOrder()
                }

                is DbState.Error -> Toast.makeText(
                    context,
                    context.getString(R.string.cart_toast_update_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    val orderDetail = when (val state = orderDetailState) {
        is UiState.Success -> state.data
        else -> null
    }
    val orderMenuList = orderDetail?.cart ?: emptyList()
    val reviewScoreMapState = remember {
        mutableStateOf<Map<String, ReviewContent>>(emptyMap())
    }

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
                    onNextClick = { menuName, reviewContent ->
                        val mutableMap = reviewScoreMapState.value.toMutableMap()
                        mutableMap[menuName] = reviewContent
                        reviewScoreMapState.value = mutableMap

                        coroutineScope.launch {
                            if (!isLastPage) {
                                pagerState.animateScrollToPage(page + 1)
                            } else {
                                val reviews = mutableListOf<Review>()
                                for ((key, value) in reviewScoreMapState.value) {
                                    val review = Review(
                                        id = null,
                                        orderKey = orderKey,
                                        reviewKey = generateReviewKey(),
                                        reviewInstant = Instant.now(),
                                        categoryType = CategoryType.Korean, // FIXME
                                        menuName = key,
                                        reviewContent = ReviewContent(
                                            reviewContent = value.reviewContent,
                                            totalScore = value.totalScore,
                                            tasteScore = value.tasteScore,
                                            recipeScore = value.recipeScore
                                        )
                                    )
                                    reviews.add(review)
                                }
                                viewModel.writeReview(reviews)
                            }
                        }
                    },
                )
            }
        }
    }
}

private fun generateReviewKey(): String {
    val currentDate = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("yyMMdd")
    val datePart = currentDate.format(dateFormatter)

    val charPool: List<Char> = ('A'..'Z') + ('0'..'9')
    val randomPart = (1..8)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
    return "R$datePart-$randomPart"
}
