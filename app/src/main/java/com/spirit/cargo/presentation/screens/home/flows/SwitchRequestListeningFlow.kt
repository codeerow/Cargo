package com.spirit.cargo.presentation.screens.home.flows

import android.content.Context
import android.content.Intent
import com.spirit.cargo.domain.order.ReadOrders
import com.spirit.cargo.domain.request.CargoRequest
import com.spirit.cargo.domain.request.commands.ReadRequest
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.REQUEST_ID_EXTRA
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.REQUEST_TITLE_EXTRA
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.REQUEST_URL_EXTRA
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.STOP_SERVICE_ACTION
import io.reactivex.rxjava3.core.Completable

class SwitchRequestListeningFlow(
    private val readRequest: ReadRequest,
    private val readOrders: ReadOrders
) {
    operator fun invoke(id: Int, turnOn: Boolean, context: Context): Completable {
        return readRequest(ReadRequest.Params(id = id))
            .flatMapCompletable { request ->
                readOrders(ReadOrders.Params(request.url)).doOnSuccess {
                    if (turnOn) context.startService(request)
                    else context.stopService(request)
                }.ignoreElement()
            }
    }

    private fun Context.startService(request: CargoRequest) {
        startService(Intent(this, RefreshOrdersInfoService::class.java).apply {
            putExtra(REQUEST_ID_EXTRA, request.id)
            putExtra(REQUEST_URL_EXTRA, request.url)
            putExtra(REQUEST_TITLE_EXTRA, request.title)
        })
    }

    private fun Context.stopService(request: CargoRequest) {
        val intentStop = Intent(this, RefreshOrdersInfoService::class.java).apply {
            putExtra(REQUEST_ID_EXTRA, request.id)
        }
        intentStop.action = STOP_SERVICE_ACTION
        startService(intentStop)
    }
}