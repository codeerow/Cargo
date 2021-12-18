package com.spirit.cargo.utils

sealed interface Optional<T : Any?> {
    data class Exist<T : Any?>(val value: T) : Optional<T>
    object None : Optional<Nothing>
}

fun <T> Optional<T>.ifExist(block: (T) -> Unit) {
    when (this) {
        is Optional.Exist -> block(value)
        else -> doNothing()
    }
}