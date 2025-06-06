package com.jin.honey.feature.district.data

import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.Coordinate
import com.jin.honey.feature.district.domain.model.District

class DistrictRepositoryImpl(private val districtDataSource: DistrictDataSource) : DistrictRepository {
    override suspend fun searchDistrictsByKeyword(keyword: String): Result<List<District>> {
        val addressList = searchDistrictAddressByKeyword(keyword)
        val placeList = searchDistrictPlaceByKeyword(keyword)

        val districtList = addressList + placeList
        return if (districtList.isNotEmpty()) {
            Result.success(districtList)
        } else {
            Result.failure(Exception("District list is empty"))
        }
    }

    private suspend fun searchDistrictAddressByKeyword(keyword: String): List<District> {
        return districtDataSource.queryAddressByKeyword(keyword)
            .getOrElse { emptyList() }
            .map { item ->
                District(
                    placeName = "",
                    address = Address(
                        lotNumAddress = item.lotNumberAddress?.addressName.orEmpty(),
                        roadAddress = item.roadAddressName
                    ),
                    coordinate = Coordinate(
                        x = item.x.toDouble(),
                        y = item.y.toDouble()
                    )
                )
            }
    }

    private suspend fun searchDistrictPlaceByKeyword(keyword: String): List<District> {
        return districtDataSource.queryPlaceByKeyword(keyword)
            .getOrElse { emptyList() }
            .map { item ->
                District(
                    placeName = item.placeName,
                    address = Address(
                        lotNumAddress = item.lotNumAddressName,
                        roadAddress = item.roadAddressName
                    ),
                    coordinate = Coordinate(
                        x = item.x.toDouble(),
                        y = item.y.toDouble()
                    )
                )
            }
    }
}
