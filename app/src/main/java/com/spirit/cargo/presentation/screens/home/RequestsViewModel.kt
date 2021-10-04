package com.spirit.cargo.presentation.screens.home

import com.spirit.cargo.domain.navigation.commands.NavigateToCreateRequest
import com.spirit.cargo.domain.request.commands.ObserveRequests
import com.spirit.cargo.presentation.screens.home.flows.DeleteRequestFlow
import com.spirit.cargo.presentation.screens.home.flows.SwitchRequestListeningFlow
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class RequestsViewModel(
    observeRequests: ObserveRequests,
    private val deleteRequestFlow: DeleteRequestFlow,
    private val switchRequestListeningFlow: SwitchRequestListeningFlow,
    private val navigateToCreateRequest: NavigateToCreateRequest
) : BaseRequestsViewModel() {

    override fun startDeleteRequestFlow(id: Int) {
        deleteRequestFlow(id = id)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun startListeningRequestFlow(id: Int, turnOn: Boolean) {
        switchRequestListeningFlow(id = id, turnOn = turnOn)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun startListeningRequestsFlow(ids: List<Int>, turnOn: Boolean) {
        Observable.fromIterable(ids)
            .flatMapCompletable { id -> switchRequestListeningFlow(id = id, turnOn = turnOn) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun startRequestCreationFlow(): Unit = run { navigateToCreateRequest().subscribe() }


    override val entities: BehaviorSubject<List<Model>> = BehaviorSubject.createDefault(listOf())

    init {
        observeRequests()
            .observeOn(Schedulers.io())
            .map { requests -> requests.map(Model::fromDomain) }
            .doOnNext(entities::onNext)
            .subscribe()
    }
}