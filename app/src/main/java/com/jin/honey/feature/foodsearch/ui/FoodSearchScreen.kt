package com.jin.honey.feature.foodsearch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBackIosNew
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.feature.ui.state.SearchState
import com.jin.honey.R
import com.jin.domain.food.model.MenuPreview
import com.jin.ui.theme.DistrictSearchHintTextColor
import com.jin.ui.theme.FoodRecentSearchKeywordDeleteTextColor
import com.jin.ui.theme.FoodSearchBoxBorderColor
import com.jin.ui.theme.FoodSearchReviewCountColor
import com.jin.ui.theme.PointColor
import com.jin.ui.theme.ReviewStarColor
import kotlinx.coroutines.delay

@Composable
fun FoodSearchScreen(
    viewModel: FoodSearchViewModel,
    menus: List<MenuPreview>?,
    onNavigateToIngredient: (menuName: String) -> Unit
) {
    val menuSearchState by viewModel.menuSearchState.collectAsState()
    var menuSearchKeyword by remember { mutableStateOf("") }
    val recentSearchKeywords by viewModel.searchKeywordState.collectAsState()
    val focusRequester = remember { FocusRequester() }

    val menuSearchList = when (val state = menuSearchState) {
        is SearchState.Success -> state.data
        else -> emptyList()
    }

    LaunchedEffect(menuSearchKeyword) {
        viewModel.searchMenuByKeyword(menuSearchKeyword)
    }

    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
        Column(modifier = Modifier.padding(innerpadding)) {
            // toolbar
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton({}) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = stringResource(R.string.ingredient_back_icon_desc),
                        tint = Color.Black
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, FoodSearchBoxBorderColor, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.district_search_icon_desc),
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        BasicTextField(
                            value = menuSearchKeyword,
                            onValueChange = { menuSearchKeyword = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            decorationBox = { innerTextField ->
                                if (menuSearchKeyword.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.food_search_hint),
                                        color = DistrictSearchHintTextColor,
                                        fontSize = 16.sp
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                // 음식 검색 결과 리스트
                if (menuSearchList.isEmpty()) {
                    // 최근 검색어
                    Column {
                        if (recentSearchKeywords.isNotEmpty()) {
                            Column {
                                Row(
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(R.string.food_search_recent_search_keyword),
                                        modifier = Modifier.weight(1f),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = stringResource(R.string.food_search_recent_search_keyword_all_delete),
                                        fontSize = 14.sp,
                                        color = FoodRecentSearchKeywordDeleteTextColor,
                                        modifier = Modifier.clickable { viewModel.clearSearchKeyword() }
                                    )
                                }
                                LazyRow(
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    items(recentSearchKeywords.size) {
                                        val searchWord = recentSearchKeywords[it]
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(30.dp))
                                                .background(Color.White)
                                                .border(1.dp, FoodSearchBoxBorderColor, RoundedCornerShape(30.dp))
                                                .clickable { onNavigateToIngredient(searchWord) }
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(searchWord, fontSize = 14.sp)
                                                Icon(
                                                    Icons.Default.Close,
                                                    contentDescription = stringResource(R.string.food_search_recent_search_keyword_delete_icon_desc),
                                                    modifier = Modifier
                                                        .scale(0.7f)
                                                        .clickable { viewModel.deleteSearchKeyword(searchWord) },
                                                    tint = FoodRecentSearchKeywordDeleteTextColor
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // 추천 메뉴
                        Column {
                            Text(
                                text = stringResource(R.string.food_search_recommend_menu),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (menus.isNullOrEmpty()) {
                                // FIXME 적절한 예외 처리 필요
                            } else {
                                RecommendMenuGrid(menus, onNavigateToIngredient)
                            }
                        }
                    }
                } else {
                    LazyColumn {
                        items(menuSearchList.size) {
                            val menuSearchItem = menuSearchList[it]
                            SearchItem(
                                menu = menuSearchItem,
                                keyword = menuSearchKeyword,
                                onNavigateToIngredient = onNavigateToIngredient,
                                onSaveSearchKeyword = { menuName -> viewModel.saveSearchKeyword(menuName) })
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun RecommendMenuGrid(menus: List<MenuPreview>, onNavigateToIngredient: (menuName: String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(menus.size) { index ->
            val menu = menus[index]
            Column(modifier = Modifier.clickable { onNavigateToIngredient(menu.menuName) }) {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                ) {
                    AsyncImage(
                        model = menu.menuImageUrl,
                        contentDescription = stringResource(R.string.food_search_recommend_menu_img_desc),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .padding(start = 4.dp, top = 4.dp)
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(1.dp, PointColor, shape = CircleShape)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_chat_honey_bee), // FIXME
                            contentDescription = stringResource(R.string.food_search_recommend_menu_category_img_desc),
                            modifier = Modifier.scale(0.7f)
                        )
                    }
                }
                Text(
                    text = menu.menuName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        imageVector = Icons.Default.Star,
                        contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                        tint = ReviewStarColor,
                    )
                    Text("4.9", fontSize = 12.sp, lineHeight = 1.5.em, fontWeight = FontWeight.SemiBold)
                    Text(
                        "(2,862)",
                        fontSize = 12.sp,
                        lineHeight = 1.5.em,
                        color = FoodSearchReviewCountColor
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchItem(
    menu: MenuPreview,
    keyword: String,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onSaveSearchKeyword: (menuName: String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onNavigateToIngredient(menu.menuName)
                onSaveSearchKeyword(menu.menuName)
            }
    ) {
        Image(
            painter = painterResource(R.drawable.img_chat_honey_bee), // FIXME
            contentDescription = stringResource(R.string.food_search_recommend_menu_category_img_desc),
            modifier = Modifier
                .size(28.dp)
                .padding(end = 8.dp)
        )
        Text("${menu.type.categoryName} > ", color = Color.Gray)
        Text(highlightText(menu.menuName, keyword))
    }
}

@Composable
private fun highlightText(source: String, keyword: String): AnnotatedString {
    if (keyword.isBlank()) return AnnotatedString(source)
    return buildAnnotatedString {
        val keywordRegex = Regex(keyword)
        var lastIndex = 0
        keywordRegex.findAll(source).forEach { matchResult ->
            val start = matchResult.range.first
            val end = matchResult.range.last + 1

            append(source.substring(lastIndex, start))
            withStyle(style = SpanStyle(color = PointColor, fontWeight = FontWeight.Bold)) {
                append(source.substring(start, end))
            }
            lastIndex = end
        }

        if (lastIndex < source.length) {
            append(source.substring(lastIndex))
        }
    }
}
