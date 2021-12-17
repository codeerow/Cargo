package com.spirit.cargo.presentation.screens.createRequest

import com.spirit.cargo.presentation.screens.createRequest.flows.CreateRequestFlow

class CreateRequestViewModel(private val createRequestFlow: CreateRequestFlow) :
    BaseCreateRequestViewModel() {

    init {
        bindChanges(createRequestFlow.changes)
    }

    override fun startCreateRequestFlow(title: String, url: String) {
        createRequestFlow(title = title, url = url).startAsync()
    }
}
