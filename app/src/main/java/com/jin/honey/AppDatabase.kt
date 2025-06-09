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
import com.jin.honey.feature.payment.data.PayAndOrderTrackingDataSource
import com.jin.honey.feature.payment.data.model.PaymentEntity

@Database(entities = [FoodEntity::class, CartEntity::class, DistrictEntity::class, PaymentEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodTrackingDataSource(): FoodTrackingDataSource
    abstract fun cartTrackingDataSource(): CartTrackingDataSource
    abstract fun districtTrackingDataSource(): DistrictTrackingDataSource
    abstract fun payAndOrderTrackingDataSource(): PayAndOrderTrackingDataSource
}
