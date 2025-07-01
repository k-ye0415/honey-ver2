package com.jin.domain.usecase

import androidx.paging.PagingData
import com.jin.domain.chat.ChatRepository
import com.jin.domain.chat.model.ChatItem
import kotlinx.coroutines.flow.Flow

class GetMessageListUseCase(private val chatRepository: ChatRepository) {
    operator fun invoke(menuName: String): Flow<PagingData<ChatItem>> = chatRepository.fetchMessageListByMenu(menuName)
}
