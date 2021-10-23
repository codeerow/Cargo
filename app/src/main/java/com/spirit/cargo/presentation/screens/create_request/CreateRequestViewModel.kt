package com.spirit.cargo.presentation.screens.create_request

import com.spirit.cargo.presentation.screens.create_request.flows.CreateRequestFlow

class CreateRequestViewModel(private val createRequestFlow: CreateRequestFlow) :
    BaseCreateRequestViewModel() {

    init {
        bindChanges(createRequestFlow.changes)
    }

    override fun startCreateRequestFlow(title: String, url: String) {
        createRequestFlow(title = title, url = url).startAsync()
    }
}
