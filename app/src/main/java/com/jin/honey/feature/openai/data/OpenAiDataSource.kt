package com.jin.honey.feature.openai.data

import com.jin.honey.feature.openai.domain.ChatItem

interface OpenAiDataSource {
    suspend fun requestChatCompletion(message: String): Result<ChatItem>
}
