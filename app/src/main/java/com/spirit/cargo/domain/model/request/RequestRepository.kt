package com.spirit.cargo.domain.model.request

import com.spirit.cargo.domain.model.request.model.CargoRequest
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface RequestRepository {
    fun create(title: String, url: String): Completable
    fun delete(id: Int): Completable
    fun observe() : Observable<List<CargoRequest>>
    fun read(id: Int): Single<CargoRequest>
    fun update(request: CargoRequest): Completable
}