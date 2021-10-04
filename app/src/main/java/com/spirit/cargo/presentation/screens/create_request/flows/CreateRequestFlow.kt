package com.spirit.cargo.presentation.screens.create_request.flows

import com.spirit.cargo.domain.request.commands.CreateRequest

class CreateRequestFlow(private val createRequest: CreateRequest) {
    operator fun invoke(title: String, url: String) =
        createRequest(CreateRequest.Params(title = title, url = url))
}