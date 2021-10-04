package com.spirit.cargo.domain.request.commands

import com.spirit.cargo.domain.request.CargoRequest
import io.reactivex.rxjava3.core.Completable


interface UpdateRequest {
    operator fun invoke(params: Params): Completable

    data class Params(val request: CargoRequest)
}