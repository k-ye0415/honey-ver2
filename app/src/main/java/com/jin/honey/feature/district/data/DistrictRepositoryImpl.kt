package com.jin.honey.feature.district.data

import com.jin.honey.feature.district.data.model.DistrictEntity
import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.AddressName
import com.jin.honey.feature.district.domain.model.Coordinate
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.AddressTag
import com.jin.honey.feature.district.domain.model.UserAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DistrictRepositoryImpl(
    private val districtDataSource: DistrictDataSource,
    private val db: DistrictTrackingDataSource
) : DistrictRepository {
    override suspend fun findUserDistrict(): Result<List<UserAddress>> {
        return try {
            withContext(Dispatchers.IO) {
                val districtEntities = db.queryDistrict()
                if (districtEntities.isNullOrEmpty()) Result.failure(Exception("District List is empty"))
                else {
                    val userAddresses = mutableListOf<UserAddress>()
                    for (entity in districtEntities) {
                        val userAddress = UserAddress(
                            id = entity.id,
                            addressTag = AddressTag.valueOf(entity.districtType),
                            address = Address(
                                placeName = entity.placeName,
                                addressName = AddressName(
                                    lotNumAddress = entity.lotNumberAddress,
                                    roadAddress = entity.roadAddress
                                ),
                                coordinate = Coordinate(x = entity.coordinateX, y = entity.coordinateY)
                            ),
                            addressDetail =  entity.detailAddress
                        )
                        userAddresses.add(userAddress)
                    }
                    Result.success(userAddresses)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchDistrictsByKeyword(keyword: String): Result<List<Address>> {
        val addressList = searchDistrictAddressByKeyword(keyword)
        val placeList = searchDistrictPlaceByKeyword(keyword)

        val districtList = addressList + placeList
        return if (districtList.isNotEmpty()) {
            Result.success(districtList)
        } else {
            Result.failure(Exception("District list is empty"))
        }
    }

    override suspend fun saveUserDistrict(userAddress: UserAddress): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                val districtEntities = db.queryDistrict() ?: emptyList()
                if (districtEntities.size >= 10) {
                    Result.failure(Exception("District is Full"))
                } else {
                    db.saveDistrict(userAddress.toEntityModel())
                    Result.success(Unit)
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception())
        }
    }

    override suspend fun deleteAndSaveDistrict(userAddress: UserAddress): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                val latestDistrict = db.latestDistrict()
                val deleteResult = db.deleteLatestDistrict(latestDistrict)
                if (deleteResult > 0) {
                    db.saveDistrict(userAddress.toEntityModel())
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("District delete is fail"))
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception())
        }
    }

    private suspend fun searchDistrictAddressByKeyword(keyword: String): List<Address> {
        return districtDataSource.queryAddressByKeyword(keyword)
            .getOrElse { emptyList() }
            .map { item ->
                Address(
                    placeName = "",
                    addressName = AddressName(
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

    private suspend fun searchDistrictPlaceByKeyword(keyword: String): List<Address> {
        return districtDataSource.queryPlaceByKeyword(keyword)
            .getOrElse { emptyList() }
            .map { item ->
                Address(
                    placeName = item.placeName,
                    addressName = AddressName(
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

    private fun UserAddress.toEntityModel(): DistrictEntity {
        return DistrictEntity(
            districtType = addressTag.typeName,
            placeName = address.placeName,
            lotNumberAddress = address.addressName.lotNumAddress,
            roadAddress = address.addressName.roadAddress,
            detailAddress = addressDetail,
            coordinateX = address.coordinate.x,
            coordinateY = address.coordinate.y
        )
    }
}
