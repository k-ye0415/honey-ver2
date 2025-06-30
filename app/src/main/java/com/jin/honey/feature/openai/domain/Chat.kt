package com.jin.honey.feature.openai.domain

import java.time.Instant

data class Chat(
    val menuName: String,
    val chatList: List<ChatItem>,
)

data class ChatItem(
    val chatId: String,
    val direction: Direction,
    val dateTime: Instant,
    val content: String,
    val chatState: ChatState,
)

enum class Direction(val type: String, val value: Int) {
    INCOMING("incoming", 0), OUTGOING("outgoing", 1);

    companion object {
        fun fromDirectionValue(value: Int): Direction = entries.first { it.value == value }
    }
}

enum class ChatState(val state: Int) {
    SUCCESS(1), LOADING(0), ERROR(-1);

    companion object {
        fun findStateByValue(value: Int): ChatState = entries.find { it.state == value } ?: ERROR
    }
}
