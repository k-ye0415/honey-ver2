package com.jin.honey.feature.district.domain.model

data class UserAddress(
    val id: Int?,
    val addressTag: AddressTag,
    val address: Address,
    val addressDetail: String
)

enum class AddressTag(val typeName: String) {
    HOME("HOME"),
    CURRENT("CURRENT");
}
