package com.jin.honey.feature.openai.data

import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {
    @POST("v1/chat/completions")
    suspend fun queryOpenAiPrompt(
        @Body body: OpenAiRequest
    ): OpenAiResponse
}

data class OpenAiChat(
    val role: String,
    val content: String
)

data class OpenAiRequest(
    val model: String,
    val messages: List<OpenAiChat>
)

data class OpenAiResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: OpenAiChat
)
