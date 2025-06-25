package com.jin.honey.feature.openai.domain

interface ChatBotRepository {
    suspend fun sendMessage()
}
