package com.spirit.cargo.domain.request.commands

import com.spirit.cargo.domain.request.CargoRequest
import io.reactivex.rxjava3.core.Single

interface ReadRequest {
    operator fun invoke(params: Params): Single<CargoRequest>

    data class Params(val id: Int)
}