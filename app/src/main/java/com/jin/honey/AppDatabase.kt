package com.jin.honey

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jin.honey.feature.address.data.AddressTrackingDataSource
import com.jin.honey.feature.address.data.model.AddressEntity
import com.jin.honey.feature.cart.data.CartTrackingDataSource
import com.jin.honey.feature.cart.data.model.CartEntity
import com.jin.honey.feature.food.data.FoodTrackingDataSource
import com.jin.honey.feature.food.data.model.FoodEntity
import com.jin.honey.feature.order.data.OrderTrackingDataSource
import com.jin.honey.feature.order.data.model.OrderEntity
import com.jin.honey.feature.recipe.data.RecipeEntity
import com.jin.honey.feature.recipe.data.RecipeTrackingDataSource
import com.jin.honey.feature.review.data.ReviewEntity
import com.jin.honey.feature.review.data.ReviewTrackingDataSource

@Database(
    entities = [
        FoodEntity::class,
        CartEntity::class,
        AddressEntity::class,
        OrderEntity::class,
        ReviewEntity::class,
        RecipeEntity::class,
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
}
