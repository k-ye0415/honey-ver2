package com.jin.honey

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jin.honey.feature.food.data.Converters
import com.jin.honey.feature.food.data.FoodTrackingDataSource
import com.jin.honey.feature.food.data.model.FoodEntity

@Database(entities = [FoodEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodTrackingDataSource(): FoodTrackingDataSource
}
