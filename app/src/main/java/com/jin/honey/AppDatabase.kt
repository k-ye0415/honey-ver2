package com.jin.honey

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jin.honey.feature.cart.data.CartTrackingDataSource
import com.jin.honey.feature.cart.data.model.CartEntity
import com.jin.honey.feature.district.data.DistrictTrackingDataSource
import com.jin.honey.feature.district.data.model.DistrictEntity
import com.jin.honey.feature.food.data.FoodTrackingDataSource
import com.jin.honey.feature.food.data.model.FoodEntity
import com.jin.honey.feature.order.data.OrderTrackingDataSource
import com.jin.honey.feature.order.data.model.OrderEntity
import com.jin.honey.feature.review.data.ReviewEntity
import com.jin.honey.feature.review.data.ReviewTrackingDataSource

@Database(
    entities = [FoodEntity::class, CartEntity::class, DistrictEntity::class, OrderEntity::class, ReviewEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodTrackingDataSource(): FoodTrackingDataSource
    abstract fun cartTrackingDataSource(): CartTrackingDataSource
    abstract fun districtTrackingDataSource(): DistrictTrackingDataSource
    abstract fun payAndOrderTrackingDataSource(): OrderTrackingDataSource
    abstract fun reviewTrackingDataSource(): ReviewTrackingDataSource
}
