package com.spirit.cargo.presentation.screens.home

import com.spirit.cargo.domain.request.commands.ObserveRequests
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class RequestsViewModel(observeRequests: ObserveRequests) : BaseRequestsViewModel() {

    override val entities: BehaviorSubject<List<Model>> = BehaviorSubject.createDefault(listOf())

    init {
        observeRequests()
            .observeOn(Schedulers.io())
            .map { requests -> requests.map(Model::fromDomain) }
            .doOnNext(entities::onNext)
            .subscribe()
    }
}