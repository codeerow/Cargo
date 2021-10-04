package com.spirit.cargo.domain.request.commands

import com.spirit.cargo.domain.request.CargoRequest
import io.reactivex.rxjava3.core.Observable

interface ObserveRequests {
    operator fun invoke() : Observable<List<CargoRequest>>
}