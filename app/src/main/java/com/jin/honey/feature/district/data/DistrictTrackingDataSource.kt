package com.jin.honey.feature.district.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.honey.feature.district.data.model.DistrictEntity

@Dao
interface DistrictTrackingDataSource {
    @Insert
    suspend fun saveDistrict(district: DistrictEntity)

    @Query("SELECT * FROM district")
    suspend fun queryDistrict(): List<DistrictEntity>?
}
