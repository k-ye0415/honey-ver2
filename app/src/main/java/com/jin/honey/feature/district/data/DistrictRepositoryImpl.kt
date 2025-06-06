package com.jin.honey.feature.district.data

import com.jin.honey.feature.district.data.model.AddressItem
import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.Coordinate
import com.jin.honey.feature.district.domain.model.District

class DistrictRepositoryImpl(private val districtDataSource: DistrictDataSource) : DistrictRepository {
    override suspend fun fetchDistrict(keyword: String): Result<List<District>> {
        println("YEJIN repository")
        return districtDataSource.fetchDistricts(keyword)
            .map { result ->
                val districtList = mutableListOf<District>()
                for (addressItem in result) {
                    districtList.add(addressItem.toDomainModel())
                }
                Result.success(districtList)
            }.getOrElse {
                Result.failure(it)
            }
    }

    private fun AddressItem.toDomainModel(): District {
        return District(
            name = title.replace("<b>", "").replace("</b>", ""),
            address = Address(address = address, roadAddress = roadAddress),
            coordinate = Coordinate(x = mapx.toLong(), y = mapy.toLong())
        )
    }
}
