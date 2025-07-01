package com.jin.honey.feature.chat.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.jin.honey.R
import com.jin.domain.chat.model.ChatItem
import com.jin.domain.chat.model.ChatState
import com.jin.domain.chat.model.Direction
import com.jin.ui.theme.DistrictSearchBoxBackgroundColor
import com.jin.ui.theme.DistrictSearchHintTextColor
import com.jin.ui.theme.IncomingBubbleBackgroundColor
import com.jin.ui.theme.OutgoingBubbleBackgroundColor
import com.jin.ui.theme.PointColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ChatScreen(viewModel: ChatViewModel, menuName: String) {
    val messageItems = viewModel.messagePagingFlow(menuName).collectAsLazyPagingItems()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val showScrollToBottomButton by remember {
        derivedStateOf {
            val firstVisibleIndex = listState.firstVisibleItemIndex
            val isAtBottom = firstVisibleIndex == 0

            messageItems.itemCount > 0 && !isAtBottom
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ChatHeader(menuName)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    state = listState,
                    reverseLayout = true,
                    verticalArrangement = Arrangement.Bottom,
                    contentPadding = PaddingValues(vertical = 10.dp),
                ) {
                    items(messageItems.itemCount) { index ->
                        val item = messageItems[index]
                        if (item != null) {
                            ChatList(item)
                        } else {
                            // FIXME UI
                        }
                    }
                }

                this@Column.AnimatedVisibility(
                    visible = showScrollToBottomButton,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = DistrictSearchBoxBackgroundColor,
                            contentColor = PointColor,
                        ),
                        onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                        },
                    ) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "최신 메시지 버튼")
                    }
                }
            }
            ChatInput(onSendMessage = { viewModel.sendMessage(menuName, it) })
        }
    }
}

@Composable
fun ChatHeader(menuName: String) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton({}) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.ingredient_back_icon_desc),
                    tint = Color.Black
                )
            }
            Text(
                text = menuName,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        HorizontalDivider(thickness = 1.dp)
    }
}

@Composable
fun ChatList(chatItem: ChatItem) {
    val isIncoming = chatItem.direction ==  Direction.INCOMING

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        horizontalAlignment = if (isIncoming) Alignment.Start else Alignment.End
    ) {
        // 사용자 정보 행
        UserInfoRow(isIncoming = isIncoming)

        // 메시지 및 시간 행
        MessageRow(
            isIncoming = isIncoming,
            chatState = chatItem.chatState,
            content = chatItem.content,
            dateTime = chatItem.dateTime
        )
    }
}

@Composable
private fun UserInfoRow(isIncoming: Boolean) {
    Row(
        modifier = Modifier.padding(bottom = 4.dp),
        horizontalArrangement = if (isIncoming) Arrangement.Start else Arrangement.End
    ) {
        if (isIncoming) {
            Image(
                painterResource(R.drawable.img_chat_honey_bee),
                contentDescription = stringResource(R.string.chatbot_honey_img_desc),
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(1.dp, PointColor, CircleShape)
            )
            Text(
                text = stringResource(R.string.chatbot_honey),
                modifier = Modifier.padding(start = 4.dp)
            )
        } else {
            Text(
                text = stringResource(R.string.chatbot_user),
                modifier = Modifier.padding(end = 4.dp)
            )
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(1.dp, PointColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = stringResource(R.string.chatbot_user_icon_desc))
            }
        }
    }
}

@Composable
private fun MessageRow(isIncoming: Boolean, chatState: ChatState, content: String, dateTime: Instant) {
    Row(verticalAlignment = Alignment.Bottom) {
        val shape = if (isIncoming) {
            RoundedCornerShape(
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            )
        } else {
            RoundedCornerShape(
                topStart = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            )
        }

        if (isIncoming) {
            Box(
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .clip(shape)
                    .background(IncomingBubbleBackgroundColor)
                    .padding(8.dp)
            ) {
                when (chatState) {
                     ChatState.ERROR -> Text("응답에 실패했습니다. 다시 시도해주세요.")
                     ChatState.LOADING -> LoadingDots()
                     ChatState.SUCCESS -> Text(content)
                }
            }
            Text(
                text = formatInstantToTime(dateTime),
                fontSize = 8.sp,
                lineHeight = 1.5.em,
                modifier = Modifier.padding(start = 4.dp)
            )
        } else {
            Text(
                text = formatInstantToTime(dateTime),
                fontSize = 8.sp,
                lineHeight = 1.5.em,
                modifier = Modifier.padding(end = 4.dp)
            )
            Box(
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .clip(shape)
                    .background(OutgoingBubbleBackgroundColor)
                    .padding(8.dp)
            ) {
                Text(content)
            }
        }
    }
}

@Composable
private fun LoadingDots() {
    var dotCount by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            dotCount = (dotCount % 3) + 1
        }
    }

    val dotsLabel = List(dotCount) { "•" }.joinToString(" ")
    Text(
        text = dotsLabel, fontSize = 14.sp, modifier = Modifier
            .width(50.dp)
            .padding(horizontal = 10.dp)
    )
}

@Composable
fun ChatInput(onSendMessage: (message: String) -> Unit) {
    var keyword by remember { mutableStateOf("") }
    HorizontalDivider(thickness = 1.dp)
    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(DistrictSearchBoxBackgroundColor, RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 4.dp),
        ) {
            BasicTextField(
                value = keyword,
                onValueChange = { keyword = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { },
                decorationBox = { innerTextField ->
                    if (keyword.isEmpty()) {
                        Text(
                            text = stringResource(R.string.chatbot_search_hint),
                            color = DistrictSearchHintTextColor,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
        IconButton(
            enabled = keyword.isNotEmpty(),
            modifier = Modifier.size(30.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = OutgoingBubbleBackgroundColor,
                contentColor = PointColor,
                disabledContainerColor = DistrictSearchBoxBackgroundColor,
                disabledContentColor = OutgoingBubbleBackgroundColor
            ),
            onClick = {
                onSendMessage(keyword)
                keyword = ""
            }
        ) {
            Icon(
                modifier = Modifier.scale(0.7f),
                painter = painterResource(R.drawable.ic_send),
                contentDescription = stringResource(R.string.cart_modify_option_close_icon_desc)
            )
        }
    }
}

private fun formatInstantToTime(instant: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.getDefault())
    val localTime = instant.atZone(ZoneId.systemDefault())
    return localTime.format(formatter)
}
