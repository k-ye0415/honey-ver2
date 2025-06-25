package com.jin.honey.feature.openai.domain

import kotlinx.coroutines.flow.Flow

class GetMessageListUseCase(private val chatRepository: ChatRepository) {
    operator fun invoke(menuName: String): Flow<Chat> = chatRepository.fetchMessageListAt(menuName)
}
