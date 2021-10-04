package com.spirit.cargo.presentation.core

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject


abstract class StateFullViewModel<S : Any>(initialState: S) : RxViewModel() {

    private val stateRelay: BehaviorSubject<S> = BehaviorSubject.createDefault(initialState)
    val state: Observable<S> get() = stateRelay

    fun <C : Observable<Change<S>>> bindChanges(vararg changes: C) {
        Observable.mergeArray(*changes)
            .distinctUntilChanged()
            .map { change -> change(stateRelay.requireValue()) }
            .doOnNext(stateRelay::onNext)
            .subscribeByViewModel()
    }

    fun Completable.startAsync(): Disposable = subscribeOn(Schedulers.io()).subscribeByViewModel()
}