package com.jin.honey.feature.openai.domain

import androidx.paging.PagingData
import com.jin.model.chat.ChatItem
import kotlinx.coroutines.flow.Flow

class GetMessageListUseCase(private val chatRepository: ChatRepository) {
    operator fun invoke(menuName: String): Flow<PagingData<ChatItem>> = chatRepository.fetchMessageListByMenu(menuName)
}
