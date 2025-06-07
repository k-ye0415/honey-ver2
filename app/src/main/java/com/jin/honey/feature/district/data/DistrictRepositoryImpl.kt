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
    override suspend fun findUserDistrict(): Result<List<UserDistrict>> {
        return try {
            withContext(Dispatchers.IO) {
                val districtEntities = db.queryDistrict()
                if (districtEntities.isNullOrEmpty()) Result.failure(Exception("District List is empty"))
                else {
                    val districtList = mutableListOf<UserDistrict>()
                    for (entity in districtEntities) {
                        val userDistrict = UserDistrict(
                            id = entity.id,
                            districtType = DistrictType.valueOf(entity.districtType),
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
                    Result.success(districtList)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
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

    override suspend fun saveUserDistrict(userDistrict: UserDistrict): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                val districtEntities = db.queryDistrict() ?: emptyList()
                if (districtEntities.size >= 10) {
                    Result.failure(Exception("District is Full"))
                } else {
                    db.saveDistrict(userDistrict.toEntityModel())
                    Result.success(Unit)
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception())
        }
    }

    override suspend fun deleteAndSaveDistrict(userDistrict: UserDistrict): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                val latestDistrict = db.latestDistrict()
                val deleteResult = db.deleteLatestDistrict(latestDistrict)
                if (deleteResult > 0) {
                    db.saveDistrict(userDistrict.toEntityModel())
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("District delete is fail"))
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception())
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

    private fun UserDistrict.toEntityModel(): DistrictEntity {
        return DistrictEntity(
            districtType = districtType.typeName,
            placeName = district.placeName,
            lotNumberAddress = district.address.lotNumAddress,
            roadAddress = district.address.roadAddress,
            detailAddress = districtDetail.detailAddress,
            coordinateX = district.coordinate.x,
            coordinateY = district.coordinate.y
        )
    }
}
