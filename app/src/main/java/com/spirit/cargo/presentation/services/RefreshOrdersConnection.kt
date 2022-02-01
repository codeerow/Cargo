package com.spirit.cargo.presentation.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.spirit.cargo.domain.request.RequestIdToOrdersCount
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class RefreshOrdersConnection {

    private val subject = BehaviorSubject.createDefault<RequestIdToOrdersCount>(mapOf())
    private var disposables = Disposable.empty()

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as RefreshOrdersInfoService.RefreshOrdersBinder
            disposables = binder.items
                .map { requests -> requests.map { it.id to it.ordersCount }.toMap() }
                .doOnNext { subject.onNext(it) }
                .subscribe()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            disposables.dispose()
        }
    }

    fun connect(fragment: Fragment): Observable<RequestIdToOrdersCount> {
        val lifecycleObserver = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                val intent = Intent(fragment.requireContext(), RefreshOrdersInfoService::class.java)
                fragment.requireContext().bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                fragment.requireContext().unbindService(connection)
            }
        }

        fragment.lifecycle.addObserver(lifecycleObserver)
        return subject
    }
}
