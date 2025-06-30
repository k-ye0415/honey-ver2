package com.jin.honey.feature.openai.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun fetchMessageListByMenu(menuName: String): Flow<PagingData<ChatItem>>
    suspend fun ensureInitialMessageByMenu(menuName: String)
    suspend fun sendMessage(menuName: String, message: String)
}
