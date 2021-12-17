package com.spirit.cargo.presentation.screens.createRequest.flows

import com.spirit.cargo.R
import com.spirit.cargo.domain.core.navigation.commands.NavigateBackward
import com.spirit.cargo.domain.core.validation.ValidateUrl
import com.spirit.cargo.domain.request.RequestRepository
import com.spirit.cargo.presentation.core.Change
import com.spirit.cargo.presentation.screens.createRequest.BaseCreateRequestViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.PublishSubject

typealias StateChange = Change<BaseCreateRequestViewModel.State>

class CreateRequestFlow(
    private val validateUrl: ValidateUrl,
    private val requestRepository: RequestRepository,
    private val navigateBackward: NavigateBackward
) {
    val changes: PublishSubject<StateChange> = PublishSubject.create()

    operator fun invoke(title: String, url: String): Completable =
        validateUrl(url)
            .doOnSubscribe { submitLoading() }
            .flatMapCompletable {
                if (it.urlIsBlank) Completable.fromAction { submitUrlIsNotValid() }
                else requestRepository.create(title = title, url = url)
                    .andThen(navigateBackward())
            }

    private fun submitUrlIsNotValid() = changes.onNext(
        BaseCreateRequestViewModel.ErrorChange(urlValidationError = R.string.validation_error_url_should_not_be_empty)
    )

    private fun submitLoading() = changes.onNext(BaseCreateRequestViewModel.LoadingChange())
}
