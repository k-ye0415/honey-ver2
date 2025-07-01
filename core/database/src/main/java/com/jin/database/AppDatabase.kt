package com.jin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jin.database.datasource.AddressTrackingDataSource
import com.jin.database.datasource.CartTrackingDataSource
import com.jin.database.datasource.ChatTrackingDataSource
import com.jin.database.datasource.FoodTrackingDataSource
import com.jin.database.datasource.OrderTrackingDataSource
import com.jin.database.datasource.RecipeTrackingDataSource
import com.jin.database.datasource.ReviewTrackingDataSource
import com.jin.database.entities.AddressEntity
import com.jin.database.entities.CartEntity
import com.jin.database.entities.ChatEntity
import com.jin.database.entities.FoodEntity
import com.jin.database.entities.OrderEntity
import com.jin.database.entities.RecipeEntity
import com.jin.database.entities.ReviewEntity

@Database(
    entities = [
        FoodEntity::class,
        CartEntity::class,
        AddressEntity::class,
        OrderEntity::class,
        ReviewEntity::class,
        RecipeEntity::class,
        ChatEntity::class,
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodTrackingDataSource(): FoodTrackingDataSource
    abstract fun recipeTrackingDataSource(): RecipeTrackingDataSource
    abstract fun cartTrackingDataSource(): CartTrackingDataSource
    abstract fun addressTrackingDataSource(): AddressTrackingDataSource
    abstract fun orderTrackingDataSource(): OrderTrackingDataSource
    abstract fun reviewTrackingDataSource(): ReviewTrackingDataSource
    abstract fun chatTrackingDataSource(): ChatTrackingDataSource
}
