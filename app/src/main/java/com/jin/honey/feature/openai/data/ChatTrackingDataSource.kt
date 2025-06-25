package com.jin.honey.feature.openai.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.honey.feature.openai.data.model.ChatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatTrackingDataSource {
    @Insert
    suspend fun insertMessage(chatEntity: ChatEntity)

    @Query("SELECT * FROM chat WHERE menuName = :menuName")
    fun queryMessageListByMenu(menuName:String): Flow<List<ChatEntity>>
}
