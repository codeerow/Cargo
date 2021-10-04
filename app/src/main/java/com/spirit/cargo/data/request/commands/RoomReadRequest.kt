package com.spirit.cargo.data.request.commands

import com.spirit.cargo.data.request.RequestsDao
import com.spirit.cargo.data.request.RoomRequestModel
import com.spirit.cargo.domain.request.CargoRequest
import com.spirit.cargo.domain.request.commands.ReadRequest
import io.reactivex.rxjava3.core.Single

class RoomReadRequest(private val dao: RequestsDao) : ReadRequest {
    override fun invoke(params: ReadRequest.Params): Single<CargoRequest> {
        return dao.read(id = params.id).map(RoomRequestModel::toDomain)
    }
}