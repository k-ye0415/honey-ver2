package com.jin.honey.feature.openai.data

import com.jin.honey.feature.openai.domain.ChatBotRepository

class ChatBotRepositoryImpl(private val openAiApi: OpenAiApi) : ChatBotRepository {
    override suspend fun sendMessage() {
        val openAiRequest = OpenAiRequest(
            model = MODEL,
            messages = listOf(
                OpenAiChat(
                    role = ROLE_SYSTEM,
                    content = CONTENT
                ),
                OpenAiChat(role = ROLE_USER, content = "Hello")
            )
        )
        val openAiResponse = openAiApi.queryOpenAiPrompt(openAiRequest)
        val prompt = openAiResponse.choices.firstOrNull()?.message?.content
        println("YEJIN response : $prompt")
    }

    private companion object {
        const val MODEL = "gpt-3.5-turbo"
        const val ROLE_SYSTEM = "system"
        const val ROLE_USER = "user"
        const val CONTENT = "You are a cooking teacher."
    }
}
