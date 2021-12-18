package com.spirit.cargo.presentation.screens.createRequest.flows

import com.spirit.cargo.R
import com.spirit.cargo.domain.core.navigation.commands.NavigateBackward
import com.spirit.cargo.domain.core.validation.ValidateUrl
import com.spirit.cargo.domain.request.RequestRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class CreateRequestFlow(
    private val validateUrl: ValidateUrl,
    private val requestRepository: RequestRepository,
    private val navigateBackward: NavigateBackward
) {
    private val _isLoading: PublishSubject<Boolean> = PublishSubject.create()
    val isLoading: Observable<Boolean> = _isLoading

    private val _errors: PublishSubject<Int> = PublishSubject.create()
    val errors: Observable<Int> = _errors

    operator fun invoke(title: String, url: String): Completable =
        validateUrl(url)
            .doOnSubscribe { _isLoading.onNext(true) }
            .flatMapCompletable {
                if (it.urlIsBlank) Completable.fromAction { _errors.onNext(R.string.validation_error_url_should_not_be_empty) }
                else requestRepository.create(title = title, url = url)
                    .andThen(navigateBackward())
            }
}
