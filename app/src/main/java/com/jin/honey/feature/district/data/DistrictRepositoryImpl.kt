package com.jin.honey.feature.district.data

import com.jin.honey.feature.district.data.model.DistrictEntity
import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.Coordinate
import com.jin.honey.feature.district.domain.model.District
import com.jin.honey.feature.district.domain.model.DistrictDetail
import com.jin.honey.feature.district.domain.model.DistrictType
import com.jin.honey.feature.district.domain.model.UserDistrict
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DistrictRepositoryImpl(
    private val districtDataSource: DistrictDataSource,
    private val db: DistrictTrackingDataSource
) : DistrictRepository {
    override suspend fun findUserDistrict(): List<UserDistrict> {
        return try {
            withContext(Dispatchers.IO) {
                val districtEntities = db.queryDistrict()
                if (districtEntities.isNullOrEmpty()) emptyList()
                else {
                    val districtList = mutableListOf<UserDistrict>()
                    for (entity in districtEntities) {
                        val userDistrict = UserDistrict(
                            id = entity.id,
                            districtType = DistrictType.CURRENT,
                            district = District(
                                placeName = entity.placeName,
                                address = Address(
                                    lotNumAddress = entity.lotNumberAddress,
                                    roadAddress = entity.roadAddress
                                ),
                                coordinate = Coordinate(x = entity.coordinateX, y = entity.coordinateY)
                            ),
                            districtDetail = DistrictDetail(detailAddress = entity.detailAddress)
                        )
                        districtList.add(userDistrict)
                    }
                    districtList
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

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

    override suspend fun saveUserDistrict(userDistrict: UserDistrict) {
        try {
            withContext(Dispatchers.IO) {
                val districtEntity = DistrictEntity(
                    districtType = userDistrict.districtType.typeName,
                    placeName = userDistrict.district.placeName,
                    lotNumberAddress = userDistrict.district.address.lotNumAddress,
                    roadAddress = userDistrict.district.address.roadAddress,
                    detailAddress = userDistrict.districtDetail.detailAddress,
                    coordinateX = userDistrict.district.coordinate.x,
                    coordinateY = userDistrict.district.coordinate.y
                )
                db.saveDistrict(districtEntity)
            }
        } catch (e: Exception) {
            //
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
