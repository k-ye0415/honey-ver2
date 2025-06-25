package com.jin.honey.feature.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.openai.domain.ChatItem
import com.jin.honey.feature.openai.domain.GetMessageListUseCase
import com.jin.honey.feature.openai.domain.SendMessageUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatBotViewModel(
    menuName: String,
    getMessageListUseCase: GetMessageListUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : ViewModel() {
    val messageListState: StateFlow<List<ChatItem>> = getMessageListUseCase(menuName)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun sendMessage(menuName: String, message: String) {
        viewModelScope.launch {
            sendMessageUseCase(menuName, message)
        }
    }
}
