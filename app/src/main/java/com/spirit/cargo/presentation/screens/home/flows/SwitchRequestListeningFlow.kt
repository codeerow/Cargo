package com.spirit.cargo.presentation.screens.home.flows

import android.content.Context
import android.content.Intent
import com.spirit.cargo.domain.request.CargoRequest
import com.spirit.cargo.domain.request.RequestRepository
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.REQUEST_ID_EXTRA
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.REQUEST_TITLE_EXTRA
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.REQUEST_URL_EXTRA
import com.spirit.cargo.presentation.services.RefreshOrdersInfoService.Companion.STOP_SERVICE_ACTION
import io.reactivex.rxjava3.core.Completable

class SwitchRequestListeningFlow(
    private val requestRepository: RequestRepository,
    private val context: Context,
) {

    operator fun invoke(id: Int, turnOn: Boolean): Completable {
        return requestRepository.read(id = id).flatMapCompletable { request ->
            Completable.fromAction {
                if (turnOn) context.startListening(request)
                else context.stopListening(request)
            }
        }
    }


    private fun Context.startListening(request: CargoRequest) {
        Intent(this, RefreshOrdersInfoService::class.java).apply {
            putExtra(REQUEST_ID_EXTRA, request.id)
            putExtra(REQUEST_URL_EXTRA, request.url)
            putExtra(REQUEST_TITLE_EXTRA, request.title)
        }.also(::startService)
    }

    private fun Context.stopListening(request: CargoRequest) {
        Intent(this, RefreshOrdersInfoService::class.java).apply {
            putExtra(REQUEST_ID_EXTRA, request.id)
            action = STOP_SERVICE_ACTION
        }.also(::startService)
    }
}
