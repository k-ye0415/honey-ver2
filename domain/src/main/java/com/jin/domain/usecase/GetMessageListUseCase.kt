package com.jin.domain.usecase

import androidx.paging.PagingData
import com.jin.domain.repositories.ChatRepository
import com.jin.domain.model.chat.ChatItem
import kotlinx.coroutines.flow.Flow

class GetMessageListUseCase(private val chatRepository: ChatRepository) {
    operator fun invoke(menuName: String): Flow<PagingData<ChatItem>> = chatRepository.fetchMessageListByMenu(menuName)
}
