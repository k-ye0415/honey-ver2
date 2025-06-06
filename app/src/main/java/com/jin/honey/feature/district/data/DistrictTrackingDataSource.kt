package com.jin.honey.feature.district.data

import androidx.room.Dao
import androidx.room.Insert
import com.jin.honey.feature.district.data.model.DistrictEntity

@Dao
interface DistrictTrackingDataSource {
    @Insert
    suspend fun saveDistrict(district: DistrictEntity)
}
