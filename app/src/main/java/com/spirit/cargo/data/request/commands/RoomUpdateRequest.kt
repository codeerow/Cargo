package com.spirit.cargo.data.request.commands

import com.spirit.cargo.data.request.RequestsDao
import com.spirit.cargo.data.request.RoomRequestModel
import com.spirit.cargo.domain.request.commands.UpdateRequest
import io.reactivex.rxjava3.core.Completable

class RoomUpdateRequest(private val dao: RequestsDao) : UpdateRequest {
    override fun invoke(params: UpdateRequest.Params): Completable {
        return dao.update(
            RoomRequestModel.fromDomain(params.request)
        )
    }
}