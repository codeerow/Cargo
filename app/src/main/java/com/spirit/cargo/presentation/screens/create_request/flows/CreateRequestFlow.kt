package com.spirit.cargo.presentation.screens.create_request.flows

import com.spirit.cargo.R
import com.spirit.cargo.domain.navigation.commands.NavigateBackward
import com.spirit.cargo.domain.request.commands.CreateRequest
import com.spirit.cargo.domain.validation.ValidateUrl
import io.reactivex.rxjava3.core.Single

class CreateRequestFlow(
    private val validateUrl: ValidateUrl,
    private val createRequest: CreateRequest,
    private val navigateBackward: NavigateBackward
) {
    operator fun invoke(title: String, url: String): Single<Output> =
        validateUrl(url).flatMap {
            if (it.urlIsBlank) Single.just(Output.Failure(urlValidationError = R.string.validation_error_url_should_not_be_empty))
            else createRequest(CreateRequest.Params(title = title, url = url))
                .andThen(navigateBackward())
                .andThen(Single.just(Output.Success)
            )
        }

    sealed class Output {
        data class Failure(val urlValidationError: Int) : Output()
        object Success : Output()
    }
}