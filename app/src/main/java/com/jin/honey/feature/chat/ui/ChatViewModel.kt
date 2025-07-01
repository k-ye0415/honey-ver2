package com.jin.honey.feature.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jin.honey.feature.openai.domain.EnsureInitialMessageUseCase
import com.jin.honey.feature.openai.domain.GetMessageListUseCase
import com.jin.honey.feature.openai.domain.SendMessageUseCase
import com.jin.model.chat.ChatItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getMessageListUseCase: GetMessageListUseCase,
    private val ensureInitialMessageUseCase: EnsureInitialMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : ViewModel() {

    fun messagePagingFlow(menuName: String): Flow<PagingData<ChatItem>> {
        ensureInitialMessage(menuName)
        return getMessageListUseCase(menuName).cachedIn(viewModelScope)
    }

    private fun ensureInitialMessage(menuName: String) {
        viewModelScope.launch {
            ensureInitialMessageUseCase(menuName)
        }
    }

    fun sendMessage(menuName: String, message: String) {
        viewModelScope.launch {
            sendMessageUseCase(menuName, message)
        }
    }
}
