package com.spirit.cargo.data.request.commands

import com.spirit.cargo.data.request.RequestsDao
import com.spirit.cargo.data.request.RoomRequestModel
import com.spirit.cargo.domain.request.CargoRequest
import com.spirit.cargo.domain.request.commands.ObserveRequests
import io.reactivex.rxjava3.core.Observable

class RoomObserveRequests(private val dao: RequestsDao) : ObserveRequests {
    override fun invoke(): Observable<List<CargoRequest>> {
        return dao.readAll().map { requests ->
            requests.map(RoomRequestModel::toDomain)
        }
    }
}