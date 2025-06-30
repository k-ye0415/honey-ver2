package com.jin.honey.feature.openai.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class ChatEntity(
    @PrimaryKey
    val id: String,
    val menuName: String,
    val direction: Int,
    val dateTime: Long,
    val content: String,
    val chatState: Int,
)
