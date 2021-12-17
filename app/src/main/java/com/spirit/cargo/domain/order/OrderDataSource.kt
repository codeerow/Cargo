package com.spirit.cargo.domain.order

import io.reactivex.rxjava3.core.Single

interface OrderDataSource {
    fun read(url: String): Single<Int>
}
