package com.jin.honey.feature.district.data

import com.jin.honey.feature.district.data.model.DistrictEntity
import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.AddressName
import com.jin.honey.feature.district.domain.model.AddressTag
import com.jin.honey.feature.district.domain.model.Coordinate
import com.jin.honey.feature.district.domain.model.UserAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DistrictRepositoryImpl(
    private val districtDataSource: DistrictDataSource,
    private val db: DistrictTrackingDataSource
) : DistrictRepository {
    override suspend fun fetchSavedAllAddresses(): List<UserAddress> {
        return try {
            withContext(Dispatchers.IO) {
                val districtEntities = db.queryAllAddress()
                if (districtEntities.isNullOrEmpty()) {
                    emptyList()
                } else {
                    districtEntities.map { it.toDomainModel() }
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun saveAddress(userAddress: UserAddress): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                db.saveDistrict(userAddress.toEntityModel())
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(Exception())
        }
    }

    override suspend fun deleteAddress(): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                val latestDistrict = db.oldestAddress()
                val deleteResult = db.deleteLatestDistrict(latestDistrict)
                if (deleteResult > 0) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Address delete is fail"))
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception())
        }
    }

    override suspend fun searchAddressByKeyword(keyword: String): List<Address> {
        val addressList = fetchAddressByKeyword(keyword)
        val placeList = fetchPlaceAddressByKeyword(keyword)
        return addressList + placeList
    }

    override suspend fun findLatestAddress(): UserAddress? {
        return try {
            withContext(Dispatchers.IO) {
                val entity = db.latestAddress()
                entity.toDomainModel()
            }
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun fetchAddressByKeyword(keyword: String): List<Address> {
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

    private suspend fun fetchPlaceAddressByKeyword(keyword: String): List<Address> {
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

    private fun DistrictEntity.toDomainModel(): UserAddress {
        return UserAddress(
            id = id,
            addressTag = AddressTag.valueOf(districtType),
            address = Address(
                placeName = placeName,
                addressName = AddressName(
                    lotNumAddress = lotNumberAddress,
                    roadAddress = roadAddress
                ),
                coordinate = Coordinate(x = coordinateX, y = coordinateY)
            ),
            addressDetail = detailAddress
        )
    }
}
