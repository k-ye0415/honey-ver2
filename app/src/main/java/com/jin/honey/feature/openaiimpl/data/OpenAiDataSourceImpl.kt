package com.jin.honey.feature.openaiimpl.data

import com.jin.network.OpenAiApi
import com.jin.honey.feature.openai.data.OpenAiDataSource
import com.jin.network.OpenAiChat
import com.jin.network.OpenAiRequest

class OpenAiDataSourceImpl(private val openAiApi: com.jin.network.OpenAiApi) : OpenAiDataSource {
    override suspend fun requestChatCompletion(message: String): Result<String> {
        val openAiRequest = com.jin.network.OpenAiRequest(
            model = MODEL,
            messages = listOf(
                com.jin.network.OpenAiChat(
                    role = ROLE_SYSTEM,
                    content = CONTENT
                ),
                com.jin.network.OpenAiChat(role = ROLE_USER, content = message)
            )
        )
        val openAiResponse = openAiApi.queryOpenAiPrompt(openAiRequest)
        val response = openAiResponse.choices.firstOrNull()?.message?.content
        return if (response.isNullOrEmpty()) {
            Result.failure(Exception("OpenAi is Error"))
        } else {
            Result.success(response)
        }
    }

    private companion object {
        const val MODEL = "gpt-3.5-turbo"
        const val ROLE_SYSTEM = "system"
        const val ROLE_USER = "user"
        const val CONTENT =
            "As a cooking expert, you should be able to provide detailed and helpful answers to users' cooking-related questions."
    }
}
