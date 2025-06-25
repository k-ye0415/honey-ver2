package com.jin.honey.feature.openai.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.honey.feature.openai.domain.ChatBotRepository
import kotlinx.coroutines.launch

class ChatBotViewModel(
    // FIXME
    private val chatBotRepository: ChatBotRepository
) : ViewModel() {
    init {
        sendMessage()
    }

    private fun sendMessage() {
        viewModelScope.launch {
            chatBotRepository.sendMessage()
        }
    }
}
