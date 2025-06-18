package com.jin.honey.feature.address.data

import com.jin.honey.feature.address.data.model.AddressEntity
import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.honey.feature.address.domain.model.SearchAddress
import com.jin.honey.feature.address.domain.model.AddressName
import com.jin.honey.feature.address.domain.model.AddressTag
import com.jin.honey.feature.address.domain.model.Coordinate
import com.jin.honey.feature.address.domain.model.UserAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddressRepositoryImpl(
    private val addressDataSource: AddressDataSource,
    private val db: AddressTrackingDataSource
) : AddressRepository {
    override suspend fun findAddresses(): Result<List<UserAddress>> {
        return try {
            withContext(Dispatchers.IO) {
                val addressEntities = db.queryAllAddress()
                if (addressEntities.isNullOrEmpty()) Result.failure(Exception("Address List is empty"))
                else Result.success(addressEntities.map { it.toDomainModel() })
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveAddress(userAddress: UserAddress): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                db.saveAddress(userAddress.toEntityModel())
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(Exception())
        }
    }

    override suspend fun deleteAddress(): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                val latestAddress = db.oldestAddress()
                val deleteResult = db.deleteLatestAddress(latestAddress)
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

    override suspend fun searchAddressByKeyword(keyword: String): Result<List<SearchAddress>> {
        val addressList = fetchAddressByKeyword(keyword)
        val placeList = fetchPlaceAddressByKeyword(keyword)

        val finalList = addressList + placeList
        return if (finalList.isNotEmpty()) {
            Result.success(finalList)
        } else {
            Result.failure(Exception("Address list is empty"))
        }
    }

    override suspend fun findLatestAddress(): Result<UserAddress> {
        return try {
            withContext(Dispatchers.IO) {
                val entity = db.latestAddress()
                Result.success(entity.toDomainModel())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun fetchAddressByKeyword(keyword: String): List<SearchAddress> {
        return addressDataSource.queryAddressByKeyword(keyword)
            .getOrElse { emptyList() }
            .map { item ->
                SearchAddress(
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

    private suspend fun fetchPlaceAddressByKeyword(keyword: String): List<SearchAddress> {
        return addressDataSource.queryPlaceByKeyword(keyword)
            .getOrElse { emptyList() }
            .map { item ->
                SearchAddress(
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

    private fun UserAddress.toEntityModel(): AddressEntity {
        return AddressEntity(
            addressType = addressTag.typeName,
            placeName = searchAddress.placeName,
            lotNumberAddress = searchAddress.addressName.lotNumAddress,
            roadAddress = searchAddress.addressName.roadAddress,
            detailAddress = addressDetail,
            coordinateX = searchAddress.coordinate.x,
            coordinateY = searchAddress.coordinate.y
        )
    }

    private fun AddressEntity.toDomainModel(): UserAddress {
        return UserAddress(
            id = id,
            addressTag = AddressTag.valueOf(addressType),
            searchAddress = SearchAddress(
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
