package com.spirit.cargo.data.request

import com.spirit.cargo.data.request.model.RequestsDao
import com.spirit.cargo.data.request.model.RoomRequestModel
import com.spirit.cargo.domain.request.CargoRequest
import com.spirit.cargo.domain.request.RequestRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class RoomRequestRepository(private val dao: RequestsDao) : RequestRepository {
    override fun create(title: String, url: String) = dao.create(
        RoomRequestModel(
            title = title,
            url = url,
        )
    )

    override fun delete(id: Int) = dao.delete(
        RoomRequestModel(
            id = id,
            title = "",
            url = "",
        )
    )

    override fun observe(): Observable<List<CargoRequest>> = dao.readAll().map { requests ->
        requests.map(RoomRequestModel::toDomain)
    }

    override fun read(id: Int): Single<CargoRequest> =
        dao.read(id = id).map(RoomRequestModel::toDomain)

    override fun update(request: CargoRequest) =
        dao.update(RoomRequestModel.fromDomain(request))
}
