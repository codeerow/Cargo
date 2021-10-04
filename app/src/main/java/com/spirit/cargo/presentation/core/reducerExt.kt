package com.spirit.cargo.presentation.core

interface Change<T> {
    operator fun invoke(subject: T): T
}
