package com.jin.honey.feature.openai.domain

import kotlinx.coroutines.flow.firstOrNull

class SaveFirstMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(menuName: String) {
        val existingMessages = chatRepository.fetchMessageListAt(menuName).firstOrNull()
        if (existingMessages.isNullOrEmpty()) chatRepository.saveFirstMessage(menuName)
    }
}
