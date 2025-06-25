package com.jin.honey.feature.openai.data.model

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
