package com.spirit.cargo.presentation.services

import com.spirit.cargo.domain.order.OrderDataSource
import com.spirit.cargo.domain.request.CargoRequest
import com.spirit.cargo.domain.request.RequestRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class RefreshOrdersInfoViewModel(
    private val requestRepository: RequestRepository,
    private val orderDataSource: OrderDataSource
) : BaseRefreshOrdersInfoViewModel {

    private val requestsIds = mutableSetOf<Int>()
    private val requestsIdsSubject: PublishSubject<Set<Int>> = PublishSubject.create()

    override val entities: Observable<List<CargoRequest>> = Observable.combineLatest(
        Observable.interval(REFRESH_PERIOD, TimeUnit.SECONDS, Schedulers.io()),
        requestsIdsSubject.observeOn(Schedulers.io())
    ) { _, ids -> ids }
        .switchMapSingle { ids ->
            Observable.fromIterable(ids)
                .flatMapSingle { requestRepository.read(id = it) }.toList()
                .flatMap { requests -> readOrdersInfoForEach(requests) }
        }


    override fun registerRequestId(id: Int) {
        requestsIds.add(id)
        requestsIdsSubject.onNext(requestsIds)
    }

    override fun unregisterRequestId(id: Int) {

        requestsIds.remove(id)
        requestsIdsSubject.onNext(requestsIds)
    }

    override fun hasRegisteredIds() = requestsIds.isNotEmpty()


    private fun readOrdersInfoForEach(requests: List<CargoRequest>) =
        Observable.fromIterable(requests).flatMapSingle { request ->
            orderDataSource.read(url = request.url)
                .onErrorResumeNext { Single.just(0) }
                .map { ordersCount ->
                    CargoRequest(
                        id = request.id,
                        url = request.url,
                        title = request.title,
                        orders = ordersCount,
                        isActive = request.isActive
                    )
                }
        }.toList()

    companion object {
        private const val REFRESH_PERIOD: Long = 5
    }
}
