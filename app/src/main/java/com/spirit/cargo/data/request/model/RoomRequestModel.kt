package com.spirit.cargo.data.request.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spirit.cargo.domain.model.request.model.CargoRequest


@Entity
data class RoomRequestModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val url: String,
    val isActive: Boolean,
    val orders: Int
) {
    fun toDomain() = CargoRequest(
        id = id,
        url = url,
        title = title,
        isActive = isActive,
        orders = orders
    )

    companion object {
        fun fromDomain(model: CargoRequest) = RoomRequestModel(
            id = model.id,
            title = model.title,
            url = model.url,
            isActive = model.isActive,
            orders = model.orders
        )
    }
}