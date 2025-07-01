package com.jin.domain.usecase

import com.jin.domain.chat.ChatRepository

class SendMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(menuName: String, message: String) {
        return chatRepository.saveOutgoingMessage(menuName, message)
    }
}
