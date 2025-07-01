package com.jin.data.openaiimpl

import com.jin.network.openai.OpenAiApi
import com.jin.data.openai.OpenAiDataSource
import com.jin.network.openai.OpenAiChat
import com.jin.network.openai.OpenAiRequest

class OpenAiDataSourceImpl(private val openAiApi: OpenAiApi) : OpenAiDataSource {
    override suspend fun requestChatCompletion(message: String): Result<String> {
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
