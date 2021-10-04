package com.spirit.cargo.domain.navigation


interface Navigation<T> {
    fun attach(subject: T)
    fun detach(subject: T)
}