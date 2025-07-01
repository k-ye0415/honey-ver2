package com.jin.domain.usecase

import com.jin.domain.chat.ChatRepository

class EnsureInitialMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(menuName: String) {
        chatRepository.ensureInitialMessageByMenu(menuName)
    }
}
