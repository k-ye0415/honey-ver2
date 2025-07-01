package com.jin.network.openai

import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {
    @POST("v1/chat/completions")
    suspend fun queryOpenAiPrompt(
        @Body body: OpenAiRequest
    ): OpenAiResponse
}
