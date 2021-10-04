package com.spirit.cargo.domain.navigation.commands

import io.reactivex.rxjava3.core.Completable

interface NavigateBackward {
    operator fun invoke(): Completable
}