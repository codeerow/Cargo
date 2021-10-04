package com.spirit.cargo.domain.model.order

import io.reactivex.rxjava3.core.Single


interface OrderRepository {
    fun read(url: String): Single<Int>
}