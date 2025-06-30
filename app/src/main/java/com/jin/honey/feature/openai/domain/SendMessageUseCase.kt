package com.jin.honey.feature.openai.domain

class SendMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(menuName: String, message: String) {
        return chatRepository.saveOutgoingMessage(menuName, message)
    }
}
