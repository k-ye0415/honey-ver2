package com.jin.honey.feature.openai.domain

import androidx.paging.PagingData
import com.jin.model.chat.ChatItem
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun fetchMessageListByMenu(menuName: String): Flow<PagingData<ChatItem>>
    suspend fun ensureInitialMessageByMenu(menuName: String)
    suspend fun saveOutgoingMessage(menuName: String, message: String)
}
