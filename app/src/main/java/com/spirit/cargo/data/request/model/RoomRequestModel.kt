package com.spirit.cargo.data.request.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spirit.cargo.domain.request.CargoRequest

@Entity
data class RoomRequestModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "url")
    val url: String,
) {
    fun toDomain() = CargoRequest(
        id = id,
        url = url,
        title = title,
    )

    companion object {
        fun fromDomain(model: CargoRequest) = RoomRequestModel(
            id = model.id,
            title = model.title,
            url = model.url,
        )
    }
}
