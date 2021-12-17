package com.spirit.cargo.presentation.services

import com.spirit.cargo.domain.request.CargoRequest
import io.reactivex.rxjava3.core.Observable

interface BaseRefreshOrdersInfoViewModel {
    val entities: Observable<List<CargoRequest>>

    fun registerRequestId(id: Int)
    fun unregisterRequestId(id: Int)
    fun hasRegisteredIds(): Boolean
}
