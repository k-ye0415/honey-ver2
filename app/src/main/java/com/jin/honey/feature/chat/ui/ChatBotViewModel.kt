package com.jin.honey.feature.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.openai.domain.ChatItem
import com.jin.honey.feature.openai.domain.GetMessageListUseCase
import com.jin.honey.feature.openai.domain.SaveFirstMessageUseCase
import com.jin.honey.feature.openai.domain.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatBotViewModel(
    private val getMessageListUseCase: GetMessageListUseCase,
    private val saveFirstMessageUseCase: SaveFirstMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : ViewModel() {
    private val _currentMenu = MutableStateFlow<String?>(null)

    val messageListState: StateFlow<List<ChatItem>> = _currentMenu
        .filterNotNull()
        .flatMapLatest { menuName ->
            getMessageListUseCase(menuName)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun initializeChat(menuName: String) {
        _currentMenu.value = menuName
    }

    fun initializeFirstMessage(menuName: String) {
        viewModelScope.launch {
            saveFirstMessageUseCase(menuName)
        }
    }

    fun sendMessage(menuName: String, message: String) {
        viewModelScope.launch {
            sendMessageUseCase(menuName, message)
        }
    }
}
