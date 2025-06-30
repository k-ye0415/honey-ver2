package com.jin.honey.feature.openai.data

interface OpenAiDataSource {
    suspend fun requestChatCompletion(message: String): Result<String>
}
