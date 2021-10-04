package com.spirit.cargo.data.request.commands

import com.spirit.cargo.data.request.RequestsDao
import com.spirit.cargo.data.request.RoomRequestModel
import com.spirit.cargo.domain.request.commands.DeleteRequest
import io.reactivex.rxjava3.core.Completable

class RoomDeleteRequest(private val dao: RequestsDao) : DeleteRequest {
    override fun invoke(params: DeleteRequest.Params): Completable {
        return dao.delete(
            RoomRequestModel(
                id = params.id,
                title = "",
                url = "",
                isActive = false,
                orders = 0
            )
        )
    }
}