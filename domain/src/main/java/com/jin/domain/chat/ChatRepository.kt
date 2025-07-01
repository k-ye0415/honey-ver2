package com.jin.domain.chat

import androidx.paging.PagingData
import com.jin.domain.chat.model.ChatItem
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun fetchMessageListByMenu(menuName: String): Flow<PagingData<ChatItem>>
    suspend fun ensureInitialMessageByMenu(menuName: String)
    suspend fun saveOutgoingMessage(menuName: String, message: String)
}
