package com.spirit.cargo.domain.request.commands

import io.reactivex.rxjava3.core.Completable

interface DeleteRequest {
    operator fun invoke(params: Params): Completable

    data class Params(val id: Int)
}