package com.jin.data.chat

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.jin.data.openai.OpenAiDataSource
import com.jin.database.datasource.ChatTrackingDataSource
import com.jin.database.entities.ChatEntity
import com.jin.domain.chat.ChatRepository
import com.jin.domain.chat.model.ChatItem
import com.jin.domain.chat.model.ChatState
import com.jin.domain.chat.model.Direction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class ChatRepositoryImpl(
    private val openAiDataSource: OpenAiDataSource,
    private val chatTrackingDataSource: ChatTrackingDataSource
) : ChatRepository {

    override fun fetchMessageListByMenu(menuName: String): Flow<PagingData<ChatItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { chatTrackingDataSource.queryMessageListByMenu(menuName) }
        ).flow
            .map { data ->
                data.map { it.toDomain() }
            }
    }

    override suspend fun ensureInitialMessageByMenu(menuName: String) {
        return withContext(Dispatchers.IO) {
            val count = chatTrackingDataSource.countMessagesByMenu(menuName)
            if (count == 0) {
                insertChatMessage(
                    menuName = menuName,
                    chatItem =  ChatItem(
                        chatId = generateChatId( Direction.INCOMING.value),
                        direction =  Direction.INCOMING,
                        dateTime = Instant.now(),
                        content = """안녕하세요!
                    |${menuName}에 관련된 모든 질문을 해주세요.🐝
                    |어떤 것이 궁금하세요?
                """.trimMargin(),
                        chatState =  ChatState.SUCCESS
                    )
                )
            }
        }
    }

    override suspend fun saveOutgoingMessage(menuName: String, message: String) {
        insertChatMessage(
            menuName = menuName,
            chatItem =  ChatItem(
                chatId = generateChatId( Direction.OUTGOING.value),
                direction =  Direction.OUTGOING,
                dateTime = Instant.now(),
                content = message,
                chatState =  ChatState.SUCCESS
            )
        )
        requestChatCompletion(menuName, message)
    }

    private suspend fun requestChatCompletion(menuName: String, message: String) {
        val loadingMessage =  ChatItem(
            chatId = generateChatId( Direction.INCOMING.value),
            direction =  Direction.INCOMING,
            dateTime = Instant.now(),
            content = "",
            chatState =  ChatState.LOADING
        )
        insertChatMessage(menuName, loadingMessage)

        openAiDataSource.requestChatCompletion(message)
            .onSuccess {
                val updateMsg = loadingMessage.copy(
                    dateTime = Instant.now(),
                    content = it,
                    chatState =  ChatState.SUCCESS
                )
                updateChatMessage(menuName = menuName, chatItem = updateMsg)
            }
            .onFailure { deleteChatMessage(menuName, loadingMessage) }
    }

    private suspend fun insertChatMessage(menuName: String, chatItem: ChatItem) {
        try {
            withContext(Dispatchers.IO) {
                chatTrackingDataSource.insertMessage(chatItem.toEntity(menuName))
            }
        } catch (_: Exception) {
            //
        }
    }

    private suspend fun updateChatMessage(menuName: String, chatItem: ChatItem) {
        try {
            withContext(Dispatchers.IO) {
                chatTrackingDataSource.updateMessage(chatItem.toEntity(menuName))
            }
        } catch (_: Exception) {
            //
        }
    }

    private suspend fun deleteChatMessage(menuName: String, chatItem: ChatItem) {
        try {
            withContext(Dispatchers.IO) {
                chatTrackingDataSource.deleteMessage(chatItem.toEntity(menuName))
            }
        } catch (_: Exception) {
            //
        }
    }

    private fun ChatItem.toEntity(menuName: String): ChatEntity {
        return ChatEntity(
            id = chatId,
            menuName = menuName,
            direction = direction.value,
            dateTime = dateTime.toEpochMilli(),
            content = content,
            chatState = chatState.state
        )
    }

    private fun ChatEntity.toDomain(): ChatItem {
        return  ChatItem(
            chatId = id,
            direction =  Direction.fromDirectionValue(direction),
            dateTime = Instant.ofEpochMilli(dateTime),
            content = content,
            chatState =  ChatState.findStateByValue(chatState)
        )
    }

    private fun generateChatId(direction: Int): String {
        val currentDate = LocalDate.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyMMdd")
        val datePart = currentDate.format(dateFormatter)

        val charPool: List<Char> = ('A'..'Z') + ('0'..'9')
        val randomPart = (1..8)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
        return "C-$direction-$datePart-$randomPart"
    }
}
