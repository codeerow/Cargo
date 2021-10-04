package com.spirit.cargo.domain.order

import io.reactivex.rxjava3.core.Single

interface ReadOrders {
    operator fun invoke(params: Params): Single<Int>

    data class Params(val url: String)
}