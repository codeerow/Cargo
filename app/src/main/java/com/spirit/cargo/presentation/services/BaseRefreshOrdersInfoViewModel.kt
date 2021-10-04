package com.spirit.cargo.presentation.services

import com.spirit.cargo.domain.model.request.model.CargoRequest
import io.reactivex.rxjava3.core.Observable


abstract class BaseRefreshOrdersInfoViewModel {
    abstract val entities: Observable<List<CargoRequest>>

    abstract fun registerRequestId(id: Int)
    abstract fun unregisterRequestId(id: Int)
    abstract fun hasRegisteredIds(): Boolean
}