package com.spirit.cargo.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber

fun <T : Any> Observable<T>.subscribe(
    lifecycleOwner: LifecycleOwner,
    onError: (Throwable) -> Unit = { Timber.tag(javaClass.name).e(it.stackTraceToString()) },
    onNext: (T) -> Unit = {},
) {
    subscribe(onNext, onError).disposeOnDestroy(lifecycleOwner)
}

fun <T : Any> Observable<T>.bind(
    lifecycleOwner: LifecycleOwner,
    onError: (Throwable) -> Unit = { Timber.tag(javaClass.name).e(it.stackTraceToString()) },
    onNext: (T) -> Unit = {},
) {
    observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError).disposeOnDestroy(lifecycleOwner)
}

private fun Disposable.disposeOnDestroy(lifecycleOwner: LifecycleOwner) {
    lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            this@disposeOnDestroy.dispose()
        }
    })
}
