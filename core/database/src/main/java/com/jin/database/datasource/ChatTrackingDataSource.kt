package com.jin.database.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jin.database.entities.ChatEntity

@Dao
interface ChatTrackingDataSource {
    @Insert
    suspend fun insertMessage(chatEntity: ChatEntity)

    @Update
    suspend fun updateMessage(chatEntity: ChatEntity)

    @Delete
    suspend fun deleteMessage(chatEntity: ChatEntity)

    @Query("SELECT COUNT(*) FROM chat WHERE menuName = :menuName")
    suspend fun countMessagesByMenu(menuName: String): Int

    @Query("SELECT * FROM chat WHERE menuName = :menuName ORDER BY dateTime DESC")
    fun queryMessageListByMenu(menuName: String): PagingSource<Int, ChatEntity>
}
