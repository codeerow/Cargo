package com.spirit.cargo.presentation.core

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

abstract class StateFulViewModel<S : Any>(initialState: S) : RxViewModel() {

    private val stateRelay: BehaviorSubject<S> = BehaviorSubject.createDefault(initialState)
    val state: Observable<S> get() = stateRelay

    fun <C : Observable<Change<S>>> bindChanges(vararg changes: C) {
        Observable.mergeArray(*changes)
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .map { change -> change(requireNotNull(stateRelay.value) { "State has not been initialized" }) }
            .doOnNext(stateRelay::onNext)
            .subscribeByViewModel()
    }
}