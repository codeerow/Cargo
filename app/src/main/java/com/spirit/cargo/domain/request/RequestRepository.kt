package com.spirit.cargo.domain.request

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface RequestRepository {
    fun delete(id: Int): Completable
    fun read(id: Int): Single<CargoRequest>
    fun update(request: CargoRequest): Completable
    fun observe() : Observable<List<CargoRequest>>
    fun create(title: String, url: String): Completable
}
