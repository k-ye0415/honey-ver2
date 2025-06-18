package com.jin.honey.feature.address.domain.model

data class UserAddress(
    val id: Int?,
    val addressTag: AddressTag,
    val searchAddress: SearchAddress,
    val addressDetail: String
)

enum class AddressTag(val typeName: String) {
    HOME("HOME"),
    CURRENT("CURRENT");
}
