package com.spirit.cargo.presentation.services

import com.spirit.cargo.domain.order.OrderDataSource
import com.spirit.cargo.domain.request.CargoRequest
import com.spirit.cargo.domain.request.RequestRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class RefreshOrdersInfoViewModel(
    private val requestRepository: RequestRepository,
    private val orderDataSource: OrderDataSource
) : BaseRefreshOrdersInfoViewModel {

    private val requestsIds = mutableSetOf<Int>()
    private val requestsIdsSubject: BehaviorSubject<Set<Int>> = BehaviorSubject.create()

    override val entities: Observable<List<BaseRefreshOrdersInfoViewModel.Item>> =
        Observable.combineLatest(
            Observable.interval(REFRESH_PERIOD, TimeUnit.SECONDS, Schedulers.io()),
            requestsIdsSubject.observeOn(Schedulers.io())
        ) { _, ids -> ids }
            .switchMapSingle { ids ->
                Observable.fromIterable(ids)
                    .flatMapSingle { requestRepository.read(id = it) }.toList()
                    .flatMap(::mapToItemForEach)
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


    private fun mapToItemForEach(requests: List<CargoRequest>) =
        Observable.fromIterable(requests).flatMapSingle { request ->
            orderDataSource.read(url = request.url)
                .onErrorResumeNext { Single.just(0) }
                .map { request.toItem(ordersCount = it) }
        }.toList()


    private fun CargoRequest.toItem(ordersCount: Int) = BaseRefreshOrdersInfoViewModel.Item(
        id = id,
        title = title,
        ordersCount = ordersCount
    )

    companion object {
        private const val REFRESH_PERIOD: Long = 5
    }
}
