package com.jin.honey.feature.openai.data

import com.jin.honey.feature.openai.data.model.OpenAiRequest
import com.jin.honey.feature.openai.data.model.OpenAiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {
    @POST("v1/chat/completions")
    suspend fun queryOpenAiPrompt(
        @Body body: OpenAiRequest
    ): OpenAiResponse
}
