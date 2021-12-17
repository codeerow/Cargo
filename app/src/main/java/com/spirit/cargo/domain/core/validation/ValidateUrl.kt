package com.spirit.cargo.domain.core.validation

import io.reactivex.rxjava3.core.Single

class ValidateUrl {
    operator fun invoke(subject: String): Single<Result> {
        return Single.just(Result(urlIsBlank = subject.isBlank()))
    }

    data class Result(val urlIsBlank: Boolean)
}
