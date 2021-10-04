package com.spirit.cargo.presentation.screens.home.flows

import com.spirit.cargo.domain.model.request.RequestRepository

class DeleteRequestFlow(private val requestRepository: RequestRepository) {
    operator fun invoke(id: Int) = requestRepository.delete(id = id)
}