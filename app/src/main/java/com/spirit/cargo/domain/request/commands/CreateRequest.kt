package com.spirit.cargo.domain.request.commands

import io.reactivex.rxjava3.core.Completable

interface CreateRequest {
    operator fun invoke(params: Params): Completable

    data class Params(val title: String, val url: String)
}