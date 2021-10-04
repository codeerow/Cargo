package com.spirit.cargo.presentation.screens.create_request.flows

import com.spirit.cargo.R
import com.spirit.cargo.domain.model.request.RequestRepository
import com.spirit.cargo.domain.navigation.commands.NavigateBackward
import com.spirit.cargo.domain.validation.ValidateUrl
import com.spirit.cargo.presentation.core.Changes
import com.spirit.cargo.presentation.screens.create_request.BaseCreateRequestViewModel
import io.reactivex.rxjava3.core.Single

typealias StateChanges = Changes<BaseCreateRequestViewModel.State>

class CreateRequestFlow(
    private val validateUrl: ValidateUrl,
    private val requestRepository: RequestRepository,
    private val navigateBackward: NavigateBackward
) {
    operator fun invoke(title: String, url: String): Single<List<StateChanges>> = validateUrl(url).flatMap {
        if (it.urlIsBlank) Single.just(
            listOf(BaseCreateRequestViewModel.ErrorChanges(urlValidationError = R.string.validation_error_url_should_not_be_empty))
        )
        else requestRepository.create(title = title, url = url)
            .andThen(navigateBackward())
            .andThen(Single.just(listOf()))
    }
}