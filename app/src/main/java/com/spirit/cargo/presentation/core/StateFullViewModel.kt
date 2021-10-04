package com.spirit.cargo.presentation.core

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject


abstract class StateFullViewModel<S : Any>(initialState: S) : ViewModel() {

    private val stateRelay: BehaviorSubject<S> = BehaviorSubject.createDefault(initialState)
    val state: Observable<S> get() = stateRelay

    fun <C : Observable<Change<S>>> bindChanges(vararg changes: C) {
        Observable.mergeArray(*changes)
            .distinctUntilChanged()
            .map { change -> change(stateRelay.requireValue()) }
            .doOnNext(stateRelay::onNext)
            .subscribe()
    }

    fun Completable.start(): Disposable = subscribeOn(Schedulers.io()).subscribe()
}