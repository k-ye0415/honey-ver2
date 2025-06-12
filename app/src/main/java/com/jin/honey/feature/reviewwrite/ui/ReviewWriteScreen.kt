package com.jin.honey.feature.reviewwrite.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.food.domain.model.Recipe
import com.jin.honey.feature.reviewwrite.ui.content.MenuReviewWriteScreen
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.ReviewStarColor
import kotlinx.coroutines.launch

@Composable
fun ReviewWriteScreen(paymentId: Int) {
    val pagerState = rememberPagerState(initialPage = 0) { menuFallback.size }
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
                val isLastPage = page == menuFallback.lastIndex
                val btnText = if (isLastPage)
                    stringResource(R.string.review_write_btn_text_save)
                else stringResource(R.string.review_write_btn_text_next)
                MenuReviewWriteScreen(
                    menu = menuFallback[page],
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

@Preview(showBackground = true)
@Composable
fun ReviewWriteScreenPreview() {
    HoneyTheme {
        ReviewWriteScreen(1)
    }
}

val menuFallback = listOf(
    Menu(
        name = "Mable McIntosh",
        imageUrl = "https://duckduckgo.com/?q=reque",
        recipe = Recipe(cookingTime = "vehicula", recipeSteps = listOf()),
        ingredient = listOf(Ingredient(name = "Elnora Peters", quantity = "ornare", unitPrice = 9320))

    ),
    Menu(
        name = "Mable McIntosh",
        imageUrl = "https://duckduckgo.com/?q=reque",
        recipe = Recipe(cookingTime = "vehicula", recipeSteps = listOf()),
        ingredient = listOf(Ingredient(name = "Elnora Peters", quantity = "ornare", unitPrice = 9320))

    )
)
