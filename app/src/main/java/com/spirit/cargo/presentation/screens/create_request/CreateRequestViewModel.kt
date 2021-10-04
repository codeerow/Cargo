package com.spirit.cargo.presentation.screens.create_request

import com.spirit.cargo.presentation.core.reduce
import com.spirit.cargo.presentation.core.requireValue
import com.spirit.cargo.presentation.screens.create_request.flows.CreateRequestFlow
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class CreateRequestViewModel(
    private val createRequestFlow: CreateRequestFlow
) : BaseCreateRequestViewModel() {

    override val state: BehaviorSubject<State> = BehaviorSubject.createDefault(State())


    override fun startCreateRequestFlow(title: String, url: String) {
        createRequestFlow(title = title, url = url)
            .subscribeOn(Schedulers.io())
            .map { reduce(state.requireValue(), it) }
            .doOnSuccess(state::onNext)
            .subscribe()
    }
}
