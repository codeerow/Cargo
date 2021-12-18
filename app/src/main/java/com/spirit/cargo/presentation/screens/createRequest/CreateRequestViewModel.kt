package com.spirit.cargo.presentation.screens.createRequest

import com.spirit.cargo.presentation.screens.createRequest.flows.CreateRequestFlow
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class CreateRequestViewModel(private val createRequestFlow: CreateRequestFlow) :
    BaseCreateRequestViewModel() {

    override val linkError: PublishSubject<Int> = PublishSubject.create()
    override val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    init {
        with(createRequestFlow) {
            errors.doOnNext {
                linkError.onNext(it)
            }.startAsync()
            isLoading.doOnNext(this@CreateRequestViewModel.isLoading::onNext).startAsync()
        }
    }

    override fun startCreateRequestFlow(title: String, url: String) {
        createRequestFlow(title = title, url = url).startAsync()
    }
}
