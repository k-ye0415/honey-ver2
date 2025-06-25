package com.jin.honey.feature.openaiimpl.data

import com.jin.honey.feature.openai.data.OpenAiApi
import com.jin.honey.feature.openai.data.OpenAiDataSource
import com.jin.honey.feature.openai.data.model.OpenAiChat
import com.jin.honey.feature.openai.data.model.OpenAiRequest
import com.jin.honey.feature.openai.domain.ChatItem
import com.jin.honey.feature.openai.domain.Direction
import java.time.Instant

class OpenAiDataSourceImpl(private val openAiApi: OpenAiApi) : OpenAiDataSource {
    override suspend fun requestChatCompletion(message: String): Result<ChatItem> {
        val openAiRequest = OpenAiRequest(
            model = MODEL,
            messages = listOf(
                OpenAiChat(
                    role = ROLE_SYSTEM,
                    content = CONTENT
                ),
                OpenAiChat(role = ROLE_USER, content = message)
            )
        )
        val openAiResponse = openAiApi.queryOpenAiPrompt(openAiRequest)
        val response = openAiResponse.choices.firstOrNull()?.message?.content
        return if (response.isNullOrEmpty()) {
            Result.failure(Exception("OpenAi is Error"))
        } else {
            val chat = ChatItem(direction = Direction.INCOMING, dateTime = Instant.now(), content = response)
            Result.success(chat)
        }
    }

    private companion object {
        const val MODEL = "gpt-3.5-turbo"
        const val ROLE_SYSTEM = "system"
        const val ROLE_USER = "user"
        const val CONTENT = "As a cooking expert, you should be able to provide detailed and helpful answers to users' cooking-related questions."
    }
}
