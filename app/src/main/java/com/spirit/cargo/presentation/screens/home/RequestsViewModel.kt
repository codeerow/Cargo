package com.spirit.cargo.presentation.screens.home

import com.spirit.cargo.domain.core.navigation.commands.NavigateToCreateRequest
import com.spirit.cargo.domain.request.RequestRepository
import com.spirit.cargo.presentation.screens.home.flows.SwitchRequestListeningFlow
import com.spirit.cargo.presentation.screens.home.model.RequestItem
import com.spirit.cargo.presentation.screens.home.model.RequestItem.Companion.toRequestItem
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class RequestsViewModel(
    private val requestRepository: RequestRepository,
    observeRequestsToOrders: Observable<List<Pair<Int, Int>>>,
    private val switchRequestListeningFlow: SwitchRequestListeningFlow,
    private val navigateToCreateRequest: NavigateToCreateRequest
) : BaseRequestsViewModel() {

    override val items: BehaviorSubject<List<RequestItem>> = BehaviorSubject.createDefault(listOf())

    init {
        requestRepository.observe()
            .withLatestFrom(observeRequestsToOrders) { requests, requestsToOrders ->
                requests.map { request ->
                    val ordersCount = requestsToOrders.find { it.first == request.id }?.second ?: 0
                    request.toRequestItem(ordersCount = ordersCount)
                }
            }.doOnNext(items::onNext).startAsync()
    }

    override fun startDeleteRequestFlow(id: Int) {
        requestRepository.delete(id = id).startAsync()
    }

    override fun startListeningRequestsFlow(turnOn: Boolean, vararg ids: Int) {
        Observable.fromIterable(ids.toList())
            .flatMapCompletable { id -> switchRequestListeningFlow(id = id, turnOn = turnOn) }
            .startAsync()
    }

    override fun startRequestCreationFlow(): Unit = run { navigateToCreateRequest() }
}
