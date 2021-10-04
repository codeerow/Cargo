package com.spirit.cargo.presentation.screens.create_request

import com.spirit.cargo.presentation.screens.create_request.flows.CreateRequestFlow
import com.spirit.cargo.utils.doNothing
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject


class CreateRequestViewModel(
    private val createRequestFlow: CreateRequestFlow
) : BaseCreateRequestViewModel() {

    override val model: BehaviorSubject<Model> = BehaviorSubject.create()


    override fun startCreateRequestFlow(title: String, url: String) {
        createRequestFlow(title = title, url = url)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                when (it) {
                    is CreateRequestFlow.Output.Success -> doNothing()
                    is CreateRequestFlow.Output.Failure -> model.onNext(Model(linkError = it.urlValidationError))
                }
            }.subscribe()
    }
}