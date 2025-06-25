package com.jin.honey.feature.openai.domain

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun fetchMessageListAt(menuName: String): Flow<List<ChatItem>>
    suspend fun sendMessage(menuName: String, message: String)
}
