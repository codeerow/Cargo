package com.spirit.cargo.domain.core.navigation

interface Navigation<T> {
    fun attach(subject: T)
    fun detach(subject: T)
}
