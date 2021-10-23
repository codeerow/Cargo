package com.spirit.cargo.domain.navigation.commands

import io.reactivex.rxjava3.core.Completable

interface NavigateToCreateRequest {
    operator fun invoke(): Completable
}