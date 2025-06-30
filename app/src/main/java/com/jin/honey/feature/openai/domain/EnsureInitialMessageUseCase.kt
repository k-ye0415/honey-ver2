package com.jin.honey.feature.openai.domain

class EnsureInitialMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(menuName: String) {
        chatRepository.ensureInitialMessageByMenu(menuName)
    }
}
