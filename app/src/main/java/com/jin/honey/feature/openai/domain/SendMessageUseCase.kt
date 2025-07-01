package com.jin.honey.feature.openai.domain

import com.jin.domain.ChatRepository

class SendMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(menuName: String, message: String) {
        return chatRepository.saveOutgoingMessage(menuName, message)
    }
}
