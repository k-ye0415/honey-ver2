package com.jin.honey.feature.openai.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.honey.feature.openai.data.model.ChatEntity

@Dao
interface ChatTrackingDataSource {
    @Insert
    suspend fun insertMessage(chatEntity: ChatEntity)

    @Query("SELECT COUNT(*) FROM chat WHERE menuName = :menuName")
    suspend fun countMessagesByMenu(menuName: String): Int

    @Query("SELECT * FROM chat WHERE menuName = :menuName ORDER BY dateTime DESC")
    fun queryMessageListByMenu(menuName: String): PagingSource<Int, ChatEntity>
}
