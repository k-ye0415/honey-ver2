package com.jin.honey.feature.openai.domain

import com.jin.domain.repositories.ChatRepository

class EnsureInitialMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(menuName: String) {
        chatRepository.ensureInitialMessageByMenu(menuName)
    }
}
