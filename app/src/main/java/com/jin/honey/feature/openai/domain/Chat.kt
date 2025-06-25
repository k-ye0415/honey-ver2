package com.jin.honey.feature.openai.domain

import java.time.Instant

data class Chat(
    val menuName: String,
    val chatList: List<ChatItem>,
)

data class ChatItem(
    val direction: Direction,
    val dateTime: Instant,
    val content: String,
)

enum class Direction(val type: String, val value: Int) {
    INCOMING("incoming", 0), OUTGOING("outgoing", 1);

    companion object {
        fun fromDirectionValue(value: Int): Direction = entries.first { it.value == value }
    }
}
