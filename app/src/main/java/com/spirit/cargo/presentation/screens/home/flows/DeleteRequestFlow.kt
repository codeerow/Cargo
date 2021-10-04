package com.spirit.cargo.presentation.screens.home.flows

import com.spirit.cargo.domain.request.commands.DeleteRequest

class DeleteRequestFlow(private val deleteRequest: DeleteRequest) {
    operator fun invoke(id: Int) = deleteRequest(DeleteRequest.Params(id = id))
}