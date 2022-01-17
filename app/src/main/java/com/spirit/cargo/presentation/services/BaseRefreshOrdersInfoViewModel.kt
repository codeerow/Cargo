package com.spirit.cargo.presentation.services

import io.reactivex.rxjava3.core.Observable

interface BaseRefreshOrdersInfoViewModel {
    val entities: Observable<List<Item>>

    fun registerRequestId(id: Int)
    fun unregisterRequestId(id: Int)
    fun hasRegisteredIds(): Boolean

    data class Item(val id: Int, val title: String, val ordersCount: Int)
}
