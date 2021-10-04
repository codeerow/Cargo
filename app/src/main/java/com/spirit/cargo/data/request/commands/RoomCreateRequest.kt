package com.spirit.cargo.data.request.commands

import com.spirit.cargo.data.request.RequestsDao
import com.spirit.cargo.data.request.RoomRequestModel
import com.spirit.cargo.domain.request.commands.CreateRequest
import io.reactivex.rxjava3.core.Completable

class RoomCreateRequest(private val dao: RequestsDao) : CreateRequest {
    override fun invoke(params: CreateRequest.Params): Completable {
        return dao.create(
            RoomRequestModel(
                title = params.title,
                url = params.url,
                isActive = false,
                orders = 0
            )
        )
    }
}