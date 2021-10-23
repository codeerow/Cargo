package com.spirit.cargo.presentation.services

import com.spirit.cargo.domain.model.order.OrderRepository
import com.spirit.cargo.domain.model.request.RequestRepository
import com.spirit.cargo.domain.model.request.model.CargoRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class RefreshOrdersInfoViewModel(
    private val requestRepository: RequestRepository,
    private val orderRepository: OrderRepository
) : BaseRefreshOrdersInfoViewModel() {

    private val requestsIds = mutableSetOf<Int>()
    private val requestsIdsSubject: PublishSubject<Set<Int>> = PublishSubject.create()

    override val entities: Observable<List<CargoRequest>> = Observable.combineLatest(
        Observable.interval(5, TimeUnit.SECONDS, Schedulers.io()),
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
            orderRepository.read(url = request.url)
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
}