package com.spirit.cargo.presentation.screens.home.flows

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.spirit.cargo.domain.request.CargoRequest
import com.spirit.cargo.domain.request.RequestRepository
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.REQUEST_ID_EXTRA
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.REQUEST_TITLE_EXTRA
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.REQUEST_URL_EXTRA
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.STOP_SERVICE_ACTION
import com.spirit.cargo.utils.doNothing
import io.reactivex.rxjava3.core.Completable
import timber.log.Timber

class SwitchRequestListeningFlow(
    private val requestRepository: RequestRepository,
    private val context: Context,
) {

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as RefreshOrdersInfoService.RefreshOrdersBinder
            binder.items.map {
                Timber.e("FUCK: ${it.map { it.id to it.ordersCount }}")
            }.subscribe()
        }

        override fun onServiceDisconnected(arg0: ComponentName) = doNothing()
    }

    operator fun invoke(id: Int, turnOn: Boolean): Completable {
        return requestRepository.read(id = id).flatMapCompletable { request ->
            Completable.fromAction {
                if (turnOn) context.startService(request)
                else context.stopService(request)
            }
        }
    }


    private fun Context.startService(request: CargoRequest) {
        Intent(this, RefreshOrdersInfoService::class.java).apply {
            putExtra(REQUEST_ID_EXTRA, request.id)
            putExtra(REQUEST_URL_EXTRA, request.url)
            putExtra(REQUEST_TITLE_EXTRA, request.title)
        }.also(::startService).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun Context.stopService(request: CargoRequest) {
        Intent(this, RefreshOrdersInfoService::class.java).apply {
            putExtra(REQUEST_ID_EXTRA, request.id)
            action = STOP_SERVICE_ACTION
        }.also(::stopService)

        unbindService(connection)
    }
}
