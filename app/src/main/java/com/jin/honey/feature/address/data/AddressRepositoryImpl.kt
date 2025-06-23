package com.jin.honey.feature.address.data

import com.jin.honey.feature.address.data.model.AddressEntity
import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.honey.feature.address.domain.model.Address
import com.jin.honey.feature.address.domain.model.AddressName
import com.jin.honey.feature.address.domain.model.AddressTag
import com.jin.honey.feature.address.domain.model.Coordinate
import com.jin.honey.feature.address.domain.model.SearchAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AddressRepositoryImpl(
    private val districtDataSource: AddressDataSource,
    private val db: AddressTrackingDataSource
) : AddressRepository {
    override fun fetchSavedAllAddresses(): Flow<List<Address>> {
        return db.queryAllAddress().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun saveAddress(address: Address): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                db.clearSelectedAddress(false)
                db.saveAddress(address.toEntityModel())
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
                val deleteResult = db.deleteLatestAddress(latestDistrict)
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

    override suspend fun searchAddressByKeyword(keyword: String): List<SearchAddress> {
        val addressList = fetchAddressByKeyword(keyword)
        val placeList = fetchPlaceAddressByKeyword(keyword)
        return addressList + placeList
    }

    override suspend fun findLatestAddress(): Address? = try {
        withContext(Dispatchers.IO) {
            val entity = db.latestAddress()
            entity.toDomainModel()
        }
    } catch (e: Exception) {
        null
    }

    override suspend fun changeCurrentAddress(address: Address): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                db.clearSelectedAddress(false)
                db.updateAddress(address.toEntityModel())
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(Exception())
        }
    }

    private suspend fun fetchAddressByKeyword(keyword: String): List<SearchAddress> {
        return districtDataSource.queryAddressByKeyword(keyword)
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
        return districtDataSource.queryPlaceByKeyword(keyword)
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

    private fun Address.toEntityModel(): AddressEntity {
        return AddressEntity(
            id = id ?: 0,
            isLatestAddress = isLatestAddress,
            addressType = addressTag.typeName,
            placeName = address.placeName,
            lotNumberAddress = address.addressName.lotNumAddress,
            roadAddress = address.addressName.roadAddress,
            detailAddress = addressDetail,
            coordinateX = address.coordinate.x,
            coordinateY = address.coordinate.y
        )
    }

    private fun AddressEntity.toDomainModel(): Address {
        return Address(
            id = id,
            isLatestAddress = isLatestAddress,
            addressTag = AddressTag.valueOf(addressType),
            address = SearchAddress(
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
