package com.jin.data.openai

interface OpenAiDataSource {
    suspend fun requestChatCompletion(message: String): Result<String>
}
