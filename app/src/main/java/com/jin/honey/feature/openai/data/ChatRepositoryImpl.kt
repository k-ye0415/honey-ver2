package com.jin.honey.feature.openai.data

import com.jin.honey.feature.openai.data.model.ChatEntity
import com.jin.honey.feature.openai.domain.ChatItem
import com.jin.honey.feature.openai.domain.ChatRepository
import com.jin.honey.feature.openai.domain.Direction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant

class ChatRepositoryImpl(
    private val openAiDataSource: OpenAiDataSource,
    private val chatTrackingDataSource: ChatTrackingDataSource
) : ChatRepository {

    override fun fetchMessageListAt(menuName: String): Flow<List<ChatItem>> {
        return chatTrackingDataSource.queryMessageListByMenu(menuName = menuName)
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun saveFirstMessage(menuName: String) {
        keepTrackChatMessageChange(
            menuName = menuName,
            chatItem = ChatItem(
                direction = Direction.INCOMING,
                dateTime = Instant.now(),
                content = """안녕하세요!
                    |${menuName}에 관련된 모든 질문을 해주세요.🐝
                    |어떤 것이 궁금하세요?
                """.trimMargin()
            )
        )
    }

    override suspend fun sendMessage(menuName: String, message: String) {
        keepTrackChatMessageChange(
            menuName = menuName,
            chatItem = ChatItem(
                direction = Direction.OUTGOING,
                dateTime = Instant.now(),
                content = message
            )
        )
        openAiDataSource.requestChatCompletion(message)
            .onSuccess { keepTrackChatMessageChange(menuName = menuName, chatItem = it) }
            .onFailure { }
    }

    private suspend fun keepTrackChatMessageChange(menuName: String, chatItem: ChatItem) {
        try {
            withContext(Dispatchers.IO) {
                chatTrackingDataSource.insertMessage(chatItem.toEntity(menuName))
            }
        } catch (_: Exception) {
            //
        }
    }

    private fun ChatItem.toEntity(menuName: String): ChatEntity {
        return ChatEntity(
            menuName = menuName,
            direction = direction.value,
            dateTime = dateTime.toEpochMilli(),
            content = content
        )
    }

    private fun ChatEntity.toDomain(): ChatItem {
        return ChatItem(
            direction = Direction.fromDirectionValue(direction),
            dateTime = Instant.ofEpochMilli(dateTime),
            content = content
        )
    }
}
