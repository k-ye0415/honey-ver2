package com.jin.honey.feature.openai.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val menuName: String,
    val direction: Int,
    val dateTime: Long,
    val content: String,
)
