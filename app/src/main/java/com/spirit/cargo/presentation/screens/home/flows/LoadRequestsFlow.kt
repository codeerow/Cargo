package com.spirit.cargo.presentation.screens.home.flows

import com.spirit.cargo.domain.model.request.RequestRepository
import com.spirit.cargo.presentation.core.Change
import com.spirit.cargo.presentation.screens.home.BaseRequestsViewModel
import com.spirit.cargo.presentation.screens.home.model.RequestItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.PublishSubject


typealias StateChange = Change<BaseRequestsViewModel.State>

class LoadRequestsFlow(private val requestRepository: RequestRepository) {
    val changes: PublishSubject<StateChange> = PublishSubject.create()

    operator fun invoke(): Completable {
        return requestRepository.observe()
            .map { it.map(RequestItem::fromDomain) }
            .doOnNext { changes.onNext(BaseRequestsViewModel.LoadRequests(it)) }
            .ignoreElements()
    }
}