package com.spirit.cargo.presentation.screens.createRequest

import com.spirit.cargo.presentation.screens.createRequest.flows.CreateRequestFlow
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class CreateRequestViewModel(private val createRequestFlow: CreateRequestFlow) :
    BaseCreateRequestViewModel() {

    override val errors: PublishSubject<Int> = PublishSubject.create()
    override val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    init {
        createRequestFlow.errors.doOnNext(errors::onNext).startAsync()
        createRequestFlow.isLoading.doOnNext(isLoading::onNext).startAsync()
    }

    override fun startCreateRequestFlow(title: String, url: String): Unit =
        run { createRequestFlow(title = title, url = url).startAsync() }
}
