package com.spirit.cargo.presentation.core

fun <T> reduce(model: T, changes: List<Changes<T>>): T {
    return changes.fold(model) { acc, change ->
        change(acc)
    }
}

interface Changes<T> {
    operator fun invoke(subject: T): T
}
